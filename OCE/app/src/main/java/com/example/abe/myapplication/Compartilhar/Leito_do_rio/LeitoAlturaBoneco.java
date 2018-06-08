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

public class LeitoAlturaBoneco extends AppCompatActivity {
    String tipo = "Altura da água no boneco";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_altura_boneco);
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
                    Log.i("Altura da água boneco", "deu bom");
                    finish();
                    Intent intent = new Intent(LeitoAlturaBoneco.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Log.i("Altura da água boneco", "deu ruim");
                }
            }
        });
    }

    public void OnClickTornozelo(View view) {
        this.sendRelato("No tornozelo", this.tipo);
    }

    public void OnClickJoelho(View view) {
        this.sendRelato("No joelho", this.tipo);
    }

    public void OnClickCintura(View view) {
        this.sendRelato("Na cintura", this.tipo);
    }

    public void OnClickPescoco(View view) {
        this.sendRelato("No pescoço", this.tipo);
    }

    public void OnClickCabeca(View view) {
        this.sendRelato("Acima da cabeça", this.tipo);
    }
}
