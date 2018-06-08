package com.example.abe.myapplication.Compartilhar.Leito_do_rio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.abe.myapplication.MainActivity;
import com.example.abe.myapplication.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class LeitoFaixaCores extends AppCompatActivity {
    private String tipo = "Faixa de cores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_faixa_cores);
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
                    Log.i("Faixa de cores", "deu bom");
                    finish();
                    Intent intent = new Intent(LeitoFaixaCores.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Log.i("Faixa de cores", "deu ruim");
                }
            }
        });
    }

    public void OnClickVermelho(View view) {
        this.sendRelato("Vermelho", this.tipo);
    }

    public void OnClickAmarelo(View view) {
        this.sendRelato("Amarelo", this.tipo);
    }

    public void OnClickVerde(View view) {
        this.sendRelato("Verde", this.tipo);
    }
}
