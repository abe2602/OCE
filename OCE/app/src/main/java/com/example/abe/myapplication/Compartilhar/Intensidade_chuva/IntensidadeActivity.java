package com.example.abe.myapplication.compartilhar.intensidade_chuva;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class IntensidadeActivity extends AppCompatActivity {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private String tipo = "Intensidade da chuva";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intensidade);

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

    public void sendRelato(String categoria, String tipo){
        ParseObject relato = new ParseObject("Relato");
        relato.put("idUser", ParseUser.getCurrentUser().getObjectId());
        relato.put("userName", ParseUser.getCurrentUser().getUsername());
        relato.put("tipoRelato", tipo);
        relato.put("Categoria", categoria);

        relato.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("Intensidade chuva", "deu bom");
                    finish();
                    Intent intent = new Intent(IntensidadeActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Log.i("Intensidade chuva", "ddeu ruim");
                }
            }
        });
    }

    public void forteComVentos(View view) {
        this.sendRelato("Chuva forte com ventos", this.tipo);
    }

    public void forteSemVentos(View view) {
        this.sendRelato("Chuva forte sem ventos", this.tipo);
    }

    public void moderadaComVentos(View view) {
        this.sendRelato("Chuva moderada com ventos", this.tipo);
    }

    public void moderadaSemVentos(View view) {
        this.sendRelato("Chuva moderada sem ventos", this.tipo);
    }

    public void fracaComVentos(View view) {
        this.sendRelato("Chuva fraca com ventos", this.tipo);
    }

    public void fracaSemVentos(View view) {
        this.sendRelato("Chuva fraca sem ventos", this.tipo);
    }
}
