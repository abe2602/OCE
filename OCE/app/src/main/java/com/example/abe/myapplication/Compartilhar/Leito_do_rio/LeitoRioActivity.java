package com.example.abe.myapplication.Compartilhar.Leito_do_rio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.abe.myapplication.R;

public class LeitoRioActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_rio);
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
