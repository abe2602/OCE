package com.example.abe.myapplication.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.LinkedList;
/*
* Classe de Login.
* Todos os métodos referentes ao login da aplicação estão aqui.
*
* Criado por Bruno Bacelar Abe
* */
public class Login extends AppCompatActivity {
    private Button cadastro;
    private EditText user, senha;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Inicialização do Parse, para que possamos usa-lo*/
        Parse.initialize(this);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", "790690493164"); //Configuração para mandar notificações

        // Configuração para criar o canal o qual as notificações acontecem
        LinkedList<String> channels = new LinkedList<String>();
        channels.push("channelName");
        installation.put("channels", channels);

        installation.saveInBackground();

        user = findViewById(R.id.editTextLogin);
        senha = findViewById(R.id.editTextPassword);
        cadastro = findViewById(R.id.buttonCadastrar);

        /*Resgata o texto salvo via SharedPreferences!!*/
        SharedPreferences preferences = getSharedPreferences("name_shared_preferences", MODE_PRIVATE);
        if(preferences.getString("stringKey", "") != null){
            String putText = preferences.getString("userName", "");
            user.setText(putText);

            putText = preferences.getString("password", "");
            senha.setText(putText);
        }
    }

    public void signInFacebook(View view) {
    }

    public void signInGmail(View view) {
    }

    public void signIn(View view) {
        SharedPreferences preferences = getSharedPreferences("name_shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("userName", user.getText().toString());
        editor.apply();

        editor.putString("password", senha.getText().toString());
        editor.apply();

        //Setting up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(Login.this);
        dlg.setTitle("Please, wait a moment.");
        dlg.setMessage("Logging in...");
        dlg.show();

        ParseUser.logInInBackground(user.getText().toString(), senha.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    dlg.dismiss();
                    alertDisplayer("Login efetuado com sucesso!","Bem-vindo(a) " + user.getText().toString() + "!");

                } else {
                    dlg.dismiss();
                    ParseUser.logOut();
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void signUp(View view) {
        this.intent = new Intent(this, cadastroActivity.class);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}


