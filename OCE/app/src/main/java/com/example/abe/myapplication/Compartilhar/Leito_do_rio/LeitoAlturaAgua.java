package com.example.abe.myapplication.Compartilhar.Leito_do_rio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.abe.myapplication.Compartilhar.Enchente_nas_ruas.AlturaRuaActivity;
import com.example.abe.myapplication.MainActivity;
import com.example.abe.myapplication.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

public class LeitoAlturaAgua extends AppCompatActivity {
    private String tipo = "Altura da água no leito do rio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_altura_agua);
    }
    public void sendRelato(String categoria, String tipo){
        ParseObject relato = new ParseObject("Relato");
        relato.put("idUser", ParseUser.getCurrentUser().getObjectId());
        relato.put("tipoRelato", tipo);
        relato.put("Categoria", categoria);

        relato.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("Altura da água no leito", "deu bom");
                    finish();
                    Intent intent = new Intent(LeitoAlturaAgua.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Log.i("Altura da água no leito", "deu ruim");
                }
            }
        });
    }

    public void onClickBaixa(View view) {
        this.sendRelato("Baixa", this.tipo);
    }

    public void onClickModerada(View view) {
        this.sendRelato("Moderada",this.tipo );
    }

    public void onClickAlta(View view) {
        this.sendRelato("Alta", this.tipo);
    }

    public void onClickTransbordando(View view) {
        this.sendRelato("Transbordando", this.tipo);
    }
}
