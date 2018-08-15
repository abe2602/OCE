package com.example.abe.myapplication.compartilhar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.abe.myapplication.compartilhar.enchente_nas_ruas.AlturaRuaActivity;
import com.example.abe.myapplication.compartilhar.foto.FotoAcitivity;
import com.example.abe.myapplication.compartilhar.intensidade_chuva.IntensidadeActivity;
import com.example.abe.myapplication.compartilhar.leito_do_rio.LeitoRioActivity;
import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;
import com.example.abe.myapplication.utils.MapsActivity;

public class MainCompartilhamento extends AppCompatActivity {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_compartilhamento);
        this.setBarClick();
    }

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

    public void onClickPhoto(View view) {
        this.intent = new Intent(this, FotoAcitivity.class);
        startActivity(intent);
    }

    public void onClickRainIntensity(View view) {
        this.intent = new Intent(this, IntensidadeActivity.class);
        startActivity(intent);
    }

    public void onClickRiver(View view) {
        this.intent = new Intent(this, LeitoRioActivity.class);
        startActivity(intent);
    }

    public void onClickWaterHigh(View view) {
        this.intent = new Intent(this, AlturaRuaActivity.class);
        startActivity(intent);
    }
}
