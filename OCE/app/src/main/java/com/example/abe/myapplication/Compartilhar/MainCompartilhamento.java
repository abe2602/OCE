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

public class MainCompartilhamento extends AppCompatActivity {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private Intent intentRua;
    private Intent intentLeito;
    private Intent intentIntensidade;
    private Intent intentFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_compartilhamento);
        this.setBarClick();
        this.buttons();

    }

    public void buttons(){
        Button buttonRua = (Button) findViewById(R.id.button_altura_ruas);
        Button buttonLeito = (Button) findViewById(R.id.button_leito_rio);
        Button buttonIntensidaade = (Button) findViewById(R.id.button_intensidade_chuva);
        Button buttonFoto = (Button) findViewById(R.id.button_enviar_foto);

        this.intentRua = new Intent(this, AlturaRuaActivity.class);
        this.intentLeito = new Intent(this, LeitoRioActivity.class);
        this.intentIntensidade = new Intent(this, IntensidadeActivity.class);
        this.intentFoto = new Intent(this, FotoAcitivity.class);

        buttonRua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentRua);
            }
        });

        buttonLeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLeito);
            }
        });

        buttonIntensidaade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentIntensidade);
            }
        });

        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentFoto);
            }
        });
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
}
