package com.example.abe.myapplication.compartilhar.foto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * Classe de compartilhamento referente a tirar fotos
 * A Latitude e longitude são postos no relato para que possamos aplicar os filtros
 * propostos, assim como recuperar o nome da rua.
 * Criado por Bruno Bacelar Abe
 * */
public class FotoAcitivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;
    private ImageView showPhoto;
    private Bitmap imageBitmap;
    private EditText comentPhoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private GoogleMap mMap;
    private double longitude;
    private double latitude;

    private LocationManager locationManager;
    private Location location;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_acitivity);
        this.setBarClick();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showPhoto = findViewById(R.id.imageView);
        comentPhoto = findViewById(R.id.editTextComent);
        this.dispatchTakePictureIntent();
    }

    /*
    * Tira a foto de fato
    * */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, SDCARD_REQUEST_CODE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /*
    * O que fazer depois que a foto é tirada
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { //caso tudo tenha dado certo
            Bundle extras = data.getExtras();
            this.imageBitmap = (Bitmap) extras.get("data"); //bitmap da imagem
            showPhoto.setImageBitmap(imageBitmap); //coloca a imagem na ImageView
        }
    }

    //Ações da barra
    public void setBarClick(){
        ImageView imageProfile = findViewById(R.id.imageProfile);
        this.intentProfile = new Intent(this, MainPerfil.class);

        ImageView imageMain = findViewById(R.id.imageHome);
        this.intentMain = new Intent(this, MainActivity.class);

        ImageView imageShare = findViewById(R.id.imageShare);
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

    //Envia o relato
    public void sendRelato(String categoria, String tipo){
        ParseObject relato = new ParseObject("Relato");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        relato.put("idUser", ParseUser.getCurrentUser().getObjectId());
        relato.put("userName", ParseUser.getCurrentUser().getUsername());
        relato.put("textoImagem", tipo);
        relato.put("Categoria", categoria);
        relato.put("tipoRelato", "foto");
        relato.put("Latitude", latitude);
        relato.put("Longitude", longitude);
        relato.put("Likes", 0);
        relato.put("Dislikes", 0);

        List<String> taps = new ArrayList<>();
        taps.add("nada");
        relato.put("RelatoRating", taps);

        imageBitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);
        byte[] relatoPhotoByteArray = stream.toByteArray();
        ParseFile photoRelato  = new ParseFile("imagem.png", relatoPhotoByteArray); //Parse só aceita byteArray
        relato.put("Imagem", photoRelato);

        relato.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("Foto", "deu bom");
                    finish();
                    Intent intent = new Intent(FotoAcitivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Log.i("Foto", "ddeu ruim");
                }
            }
        });
    }

    public void cancelPhoto(View view) {
    }

    public void savePhoto(View view) {
        this.sendRelato("Foto", this.comentPhoto.getText().toString());
    }

    /**
     * Método da Ação dentro do mapa, assim que ele fica pronto
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        this.getGPS();

        LatLng pira = new LatLng(-22.0035,  -47.8959);
        mMap.addMarker(new MarkerOptions().position(pira).title("Marker"));
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(5).target(pira).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * Métodos relacionados ao marker
     * */
    @Override
    public void onLocationChanged(Location location) {

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
