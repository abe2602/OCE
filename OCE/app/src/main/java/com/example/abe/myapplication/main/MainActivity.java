package com.example.abe.myapplication.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;


    /*Para adicionar imagens no botão, usar o seguinte atributo no xhrml:[
    *  android:drawableY="@drawable/ic_action_name"
    *  Y = Right, Left, Top, Bottom
    *  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        this.getInfoFromParse();

        recyclerView = findViewById(R.id.recycler_view_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void getInfoFromParse(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Relato");
        final String objectId =  ParseUser.getCurrentUser().getObjectId();

        query.whereNotEqualTo("idUser", objectId+"uhehue");
        query.orderByDescending("createdAt");

      //  textShare.setText(ParseUser.getCurrentUser().getUsername());

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
