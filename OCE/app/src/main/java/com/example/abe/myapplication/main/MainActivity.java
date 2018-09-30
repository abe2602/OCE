package com.example.abe.myapplication.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;
import com.example.abe.myapplication.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/*
* Classe que representa a tela inicial do app. Para exibir todos os
* relatos, usamos a estrutura "RecyclerView", uma vez que não há um
* número fixo de relatos. A RecyclerView contém um adaptador, o qual
* "linka" os dados em suas posições específicas (esse adaptador tem
* sua própria classe e é chamado "MainAdapter")
* Os dados são recuperados do servidor e uma vez recuperados, são passados
* para o adaptador (respeitando o limite, em km, que começa - por padrão -
* em 10).
*
* Criado por Bruno Bacelar Abe
* */
public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,  LocationListener{
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private LocationManager locationManager;
    private Location location;
    private LocationListener locationListener;

    static private double longitude;
    static private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d("aqui","oncreate");

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("main","gps entrei");
            if( ParseUser.getCurrentUser().getDouble("Latitude") != 0)
                latitude = ParseUser.getCurrentUser().getDouble("Latitude");

            if(ParseUser.getCurrentUser().getDouble("Longitude") != 0)
                longitude = ParseUser.getCurrentUser().getDouble("Longitude");

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0.0f, this);

        //Coloca o menu de opções no main.
        Log.d("main","toolbar");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        toggle.syncState();

        drawerLayout.addDrawerListener(toggle);

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        this.setBarClick();
        this.getInfoFromParse();

        //RecyclerView sendo instanciada
        recyclerView = findViewById(R.id.recycler_view_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    //Lida com os cliques da barra de navegação
    public void setBarClick(){
        ImageView imageProfile;
        ImageView imageMain;
        ImageView imageShare;

        // Log.e("MainActiviry", ParseUser.getCurrentUser().getUsername());
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        this.intentProfile = new Intent(this, MainPerfil.class);

        imageMain = (ImageView) findViewById(R.id.imageHome);
        this.intentMain = new Intent(this, MainActivity.class);

        imageShare = (ImageView) findViewById(R.id.imageShare);
        this.intentShare = new Intent(this, MainCompartilhamento.class);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentProfile);
            }
        });

        imageMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentMain);
            }
        });

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentShare.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentShare);
            }
        });

    }

    //Pega os dados do Banco
    public void getInfoFromParse(){
        Log.d("aqui","parse");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Relato");
        final String objectId =  ParseUser.getCurrentUser().getObjectId();
        TextView nameUser = findViewById(R.id.userNameTextView);
        CircleImageView profileImage = findViewById(R.id.mainProfileImageView);

        if(ParseUser.getCurrentUser().getParseFile("imagemPerfil") == null){
            profileImage.setImageResource(R.drawable.default_icon);
        }else{
            Utils utils = new Utils();
            ParseFile tempPhoto = ParseUser.getCurrentUser().getParseFile("imagemPerfil");
            utils.loadImagesCircle(tempPhoto, profileImage);
        }
        nameUser.setText(ParseUser.getCurrentUser().getUsername());

        //Procura os relatos
        query.whereNotEqualTo("idUser", objectId+"uhehue");
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        //Filtra os dados antes de colocar na recyclerview, só entra quem estiver no raio
                        Log.d("longlatmain", String.valueOf(longitude));
                        if(longitude == 0){
                            latitude = ParseUser.getCurrentUser().getDouble("Latitude");
                            longitude = ParseUser.getCurrentUser().getDouble("Longitude");
                        }

                        List<ParseObject> objects2 = new ArrayList<>();
                        double raio = (int)ParseUser.getCurrentUser().getNumber("raioInteresse");

                        for(int i = 0; i < objects.size(); i++){
                            objects.get(i).getNumber("Longitude").doubleValue();
                            double dist = findDistanceLat(objects.get(i).getNumber("Latitude").doubleValue(),
                                    objects.get(i).getNumber("Longitude").doubleValue() , latitude, longitude);

                            Log.d("raioDist", String.valueOf(dist));

                            if(dist < raio){
                                objects2.add(objects.get(i));
                            }
                        }

                        mainAdapter = new MainAdapter(objects2.size(), objects2, longitude, latitude);
                        recyclerView.setAdapter(mainAdapter);
                    }
                }
            }
        });
    }

    //Usado no filtro
    public void deleteData(){
        List< ParseObject> nomeDoArrayList = new ArrayList<>();
        nomeDoArrayList.clear();

        MainAdapter myAdapter = new MainAdapter(nomeDoArrayList.size(), nomeDoArrayList, longitude, latitude);
        recyclerView.setAdapter(myAdapter);
    }

    //Usado no filtro
    public void updateParse(String tipoRelato){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Relato");

        //Procura os relatos
        query.whereEqualTo("tipoRelato", tipoRelato);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                      //  Log.d("User", String.valueOf(objects.size()));
                        Log.d("longlatmain", String.valueOf(longitude));
                        List<ParseObject> objects2 = new ArrayList<>();
                        double raio = (int)ParseUser.getCurrentUser().getNumber("raioInteresse");

                        for(int i = 0; i < objects.size(); i++){
                            objects.get(i).getNumber("Longitude").doubleValue();
                            double dist = findDistanceLat(objects.get(i).getNumber("Latitude").doubleValue(),
                                    objects.get(i).getNumber("Longitude").doubleValue() , latitude, longitude);

                            if(dist < raio){
                                objects2.add(objects.get(i));
                            }
                        }

                        mainAdapter = new MainAdapter(objects2.size(), objects2, longitude, latitude);
                        recyclerView.setAdapter(mainAdapter);
                    }
                }
            }
        });
    }

    //Traduz a distancia entre duas longitudes e latitudes para km
    private double findDistanceLat(double lat_inicial, double long_inicial, double lat_final, double long_final){
        double d2r = 0.017453292519943295769236;
        double dlong = (long_final - long_inicial) * d2r;
        double dlat = (lat_final - lat_inicial) * d2r;

        double temp_sin = sin(dlat/2.0);
        double temp_cos = cos(lat_inicial * d2r);
        double temp_sin2 = sin(dlong/2.0);

        double a = (temp_sin * temp_sin) + (temp_cos * temp_cos) * (temp_sin2 * temp_sin2);
        double c = 2.0 * atan2(sqrt(a), sqrt(1.0 - a));

        return 6368.1 * c;
    }

    //Função dos filtros
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        deleteData();

        switch (item.getItemId()){
            case R.id.nav_item_altura_rua:
                Log.d("pad", "1");
                updateParse("Altura Água na rua");
                break;
            case R.id.nav_item_intensidade_chuva:
                Log.d("pad", "2");
                updateParse("Intensidade da chuva");
                break;
            case R.id.nav_item_photo:
                Log.d("pad", "3");
                updateParse("foto");
                break;
            case R.id.nav_item_rua_leito:
                Log.d("pad", "4");
                updateParse("Altura da água no leito do rio");
                break;
            case R.id.nav_item_boneco:
                Log.d("pad", "5");
                updateParse("Altura da água no boneco");
                break;
            case R.id.nav_item_cor:
                Log.d("pad", "6");
                updateParse("Faixa de cores");
                break;
            default:
                Log.d("pad", "drao");
                getInfoFromParse();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        //Guarda a última posição válida no banco
        if(longitude != 0 && latitude != 0){
            ParseUser.getCurrentUser().put("Latitude", latitude);
            ParseUser.getCurrentUser().put("Longitude", longitude);
            ParseUser.getCurrentUser().saveInBackground();
        }else{
            latitude = ParseUser.getCurrentUser().getDouble("Latitude");
            longitude = ParseUser.getCurrentUser().getDouble("Longitude");
        }

        Log.d("pegueiMain", String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
