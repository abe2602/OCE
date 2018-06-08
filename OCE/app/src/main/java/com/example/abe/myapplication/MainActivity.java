package com.example.abe.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.Compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.Perfil.MainPerfil;
import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    private ImageView imageProfile;
    private ImageView imageMain;
    private ImageView imageShare;
    private ImageView imageConfig;

    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private TextView teste;


    /*Para adicionar imagens no botão, usar o seguinte atributo no xhrml:[
    *  android:drawableY="@drawable/ic_action_name"
    *  Y = Right, Left, Top, Bottom
    *  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivity(intentShare);
            }
        });
    }
}
