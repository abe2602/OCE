package com.example.abe.myapplication.main;

import android.Manifest;
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

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
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

    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.getGPS();

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

        recyclerView = findViewById(R.id.recycler_view_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

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

    public void getInfoFromParse(){
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
                        Log.d("User", String.valueOf(objects.size()));

                        mainAdapter = new MainAdapter(objects.size(), objects, longitude, latitude);
                        recyclerView.setAdapter(mainAdapter);
                    }
                }
            }
        });
    }

    public void deleteData(){
        List< ParseObject> nomeDoArrayList = new ArrayList<>();
        nomeDoArrayList.clear();

        MainAdapter myAdapter = new MainAdapter(nomeDoArrayList.size(), nomeDoArrayList, longitude, latitude);
        recyclerView.setAdapter(myAdapter);
    }

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
                        Log.d("User", String.valueOf(objects.size()));

                        mainAdapter = new MainAdapter(objects.size(), objects, longitude, latitude);
                        recyclerView.setAdapter(mainAdapter);
                    }
                }
            }
        });
    }

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


    public void startGPS(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    public void getGPS(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("longlatC", String.valueOf(location.getLongitude()));
                Log.d("longlatC", String.valueOf(location.getLatitude()));

                longitude = location.getLongitude();
                latitude = location.getLatitude();
                //searchAdrress(location.getLongitude(), location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("longlat", String.valueOf(location.getLongitude()));
                Log.d("longlat", String.valueOf(location.getLatitude()));

                longitude = location.getLongitude();
                latitude = location.getLatitude();

                // searchAdrress(location.getLongitude(), location.getLatitude());
            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        startGPS();
    }

}
