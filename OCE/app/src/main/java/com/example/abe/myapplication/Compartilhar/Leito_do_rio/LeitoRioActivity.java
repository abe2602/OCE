package com.example.abe.myapplication.compartilhar.leito_do_rio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;

/*
 * Classe de compartilhamento referente a altura da água no leito do rio
 * A Latitude e longitude são postos no relato para que possamos aplicar os filtros
 * propostos, assim como recuperar o nome da rua.
 * Criado por Bruno Bacelar Abe
 * */
public class LeitoRioActivity extends AppCompatActivity {
    private ImageView imageProfile;
    private ImageView imageMain;
    private ImageView imageShare;
    private ImageView imageConfig;

    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_rio);

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

    public void alturaAguaLeitoClick(View view) {
        intent = new Intent(this, LeitoAlturaAgua.class);
        startActivity(intent);
    }

    public void faixaDeCoresClick(View view) {
        intent = new Intent(this, LeitoFaixaCores.class);
        startActivity(intent);
    }

    public void alturaBonecoClick(View view) {
        intent = new Intent(this, LeitoAlturaBoneco.class);
        startActivity(intent);
    }
}
