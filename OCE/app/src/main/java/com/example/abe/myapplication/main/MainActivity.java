package com.example.abe.myapplication.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    private LocationManager locationManager;
    private LocationListener listener;

    /*Para adicionar imagens no botão, usar o seguinte atributo no xhrml:[
     *  android:drawableY="@drawable/ic_action_name"
     *  Y = Right, Left, Top, Bottom
     *  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        ImageView imageConfig;

        // Log.e("MainActiviry", ParseUser.getCurrentUser().getUsername());
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        this.intentProfile = new Intent(this, MainPerfil.class);

        imageMain = (ImageView) findViewById(R.id.imageHome);
        this.intentMain = new Intent(this, MainActivity.class);

        imageShare = (ImageView) findViewById(R.id.imageShare);
        this.intentShare = new Intent(this, MainCompartilhamento.class);

        imageConfig = (ImageView) findViewById(R.id.imageOpcoes);


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

                    mainAdapter = new MainAdapter(objects.size(), objects);
                    recyclerView.setAdapter(mainAdapter);
                    }
                }
            }
        });
    }

}
