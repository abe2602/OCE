package com.example.abe.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import static android.text.TextUtils.isEmpty;

public class Login extends AppCompatActivity {

    private Button cadastro;
    private EditText user, senha;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Parser para realizar o login*/
        Parse.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        user = (EditText) findViewById(R.id.editTextLogin);
        senha = (EditText) findViewById(R.id.editTextPassword);
        cadastro = (Button) findViewById(R.id.buttonCadastrar);

    }

    @Override
    protected void onPause() {
        super.onPause();
   //     finish();
    }

    public void signInFacebook(View view) {
    }

    public void signInGmail(View view) {
    }

    public void signIn(View view) {
        boolean validationError = false;

        /*
        * FAZER A VALIDAÇÃO DOS CAMPOS!!!
        * */


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

