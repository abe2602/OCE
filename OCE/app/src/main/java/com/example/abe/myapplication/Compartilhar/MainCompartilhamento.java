package com.example.abe.myapplication.Compartilhar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.abe.myapplication.Compartilhar.Enchente_nas_ruas.AlturaRuaActivity;
import com.example.abe.myapplication.Compartilhar.Foto.FotoAcitivity;
import com.example.abe.myapplication.Compartilhar.Intensidade_chuva.IntensidadeActivity;
import com.example.abe.myapplication.Compartilhar.Leito_do_rio.LeitoRioActivity;
import com.example.abe.myapplication.MainActivity;
import com.example.abe.myapplication.Perfil.MainPerfil;
import com.example.abe.myapplication.R;

public class MainCompartilhamento extends AppCompatActivity {
    private ImageView imageProfile;
    private ImageView imageMain;
    private ImageView imageShare;

    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private Intent intentRua;
    private Intent intentLeito;
    private Intent intentIntensidade;
    private Intent intentFoto;

    private Button buttonRua;
    private Button buttonLeito;
    private Button buttonIntensidaade;
    private Button buttonFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_compartilhamento);
        this.navegationBar();
        this.buttons();

    }

    public void buttons(){
        this.buttonRua = (Button) findViewById(R.id.button_altura_ruas);
        this.buttonLeito = (Button) findViewById(R.id.button_leito_rio);
        this.buttonIntensidaade = (Button) findViewById(R.id.button_intensidade_chuva);
        this.buttonFoto = (Button) findViewById(R.id.button_enviar_foto);

        this.intentRua = new Intent(this, AlturaRuaActivity.class);
        this.intentLeito = new Intent(this, LeitoRioActivity.class);
        this.intentIntensidade = new Intent(this, IntensidadeActivity.class);
        this.intentFoto = new Intent(this, FotoAcitivity.class);

        this.buttonRua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentRua);
            }
        });

        this.buttonLeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLeito);
            }
        });

        this.buttonIntensidaade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentIntensidade);
            }
        });

        this.buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentFoto);
            }
        });
    }

    public void navegationBar(){
        this.imageProfile = (ImageView) findViewById(R.id.imageProfile);
        this.imageMain = (ImageView) findViewById(R.id.imageHome);
        this.imageShare = (ImageView) findViewById(R.id.imageShare);

        this.intentProfile = new Intent(this, MainPerfil.class);
        this.intentMain = new Intent(this, MainActivity.class);
        this.intentShare = new Intent(this, MainCompartilhamento.class);

        this.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentProfile);
            }
        });

        this.imageMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentMain);
            }
        });

        this.imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentShare);
            }
        });
    }
}
