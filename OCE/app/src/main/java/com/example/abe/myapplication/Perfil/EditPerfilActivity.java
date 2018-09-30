package com.example.abe.myapplication.perfil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.abe.myapplication.R;
import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.main.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/*
* Classe "principal" da edição do perfil. Basicamente, criados uma recyclerView onde
* cada uma das posições da mesma possui um layout diferente, uma vez que há várias
* opções diferentes para edição.
* Criado por Bruno Bacelar Abe
* */
public class EditPerfilActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;
    private RecyclerView recyclerView;
    private EditPerfilAdapter editPerfilAdapter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        this.setBarClick();
        int[] vet = new int[5];

        for (int i = 0; i < vet.length; i++){
            vet[i] = i;
        }

        recyclerView = findViewById(R.id.edit_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        editPerfilAdapter = new EditPerfilAdapter(vet);
        recyclerView.setAdapter(editPerfilAdapter);

    }

    //Ações da barra de tarefas
    public void setBarClick(){

        ImageView imageProfile = findViewById(R.id.imageProfile);
        this.intentProfile = new Intent(this, MainPerfil.class);

        ImageView imageMain = findViewById(R.id.imageHome);
        this.intentMain = new Intent(this, MainActivity.class);

        ImageView imageShare = findViewById(R.id.imageShare);
        this.intentShare = new Intent(this, MainCompartilhamento.class);

        ImageView imageConfig = findViewById(R.id.imageOpcoes);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_CANCELED){
            if(requestCode == editPerfilAdapter.PICK_IMAGE){

                Uri selectedImage = data.getData();

                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 75, stream);

                    byte[] byteArray = stream.toByteArray();
                    ParseFile fotoParse = new ParseFile("img.png", byteArray);
                    ParseUser.getCurrentUser().put("imagemPerfil", fotoParse);
                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Log.d("getImage", "sucesso");
                            }else{
                                Log.d("getImage", "falha");
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng pira = new LatLng(-22.768682,  -47.589951);
        mMap.addMarker(new MarkerOptions().position(pira).title("Marker in Piracicaba"));
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(pira).build();
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(pira));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
