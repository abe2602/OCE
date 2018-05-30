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

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class cadastroActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private EditText passwordAgainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Parse.initialize(this);

      /*  final Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(SignUpActivity.this);
                dlg.setTitle("Please, wait a moment.");
                dlg.setMessage("Returning to the login section...");
                dlg.show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                dlg.dismiss();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });*/

        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        passwordAgainView = (EditText) findViewById(R.id.passwordAgain);

        /*
        * Verifica se há campos em branco
        * */
        final Button signup_button = findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validating the log in data
                boolean validationError = false;

                StringBuilder validationErrorMessage = new StringBuilder("Por favor, preencher");
                if (isEmpty(usernameView)) {
                    validationError = true;
                    validationErrorMessage.append("o campo Usuário");
                }
                if (isEmpty(passwordView)) {
                    if (validationError) {
                        validationErrorMessage.append(" e ");
                    }
                    validationError = true;
                    validationErrorMessage.append("o campo Senha");
                }
                if (isEmpty(passwordAgainView)) {
                    if (validationError) {
                        validationErrorMessage.append(" e ");
                    }
                    validationError = true;
                    validationErrorMessage.append("a senha novamente");
                }
                else {
                    if (!isMatching(passwordView, passwordAgainView)) {
                        if (validationError) {
                            validationErrorMessage.append(" e ");
                        }
                        validationError = true;
                        validationErrorMessage.append("digite a senha duas vezes");
                    }
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(cadastroActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                /*
                * Aqui usamos o Parse para cadastrar o usuário!
                * Quando o usuário se cadastrar, ele será redirecionado para a tela de login, onde poderá
                * se logar.
                *
                * */
                final ProgressDialog dlg = new ProgressDialog(cadastroActivity.this);
                dlg.setTitle("Aguarde um momento");
                dlg.setMessage("Cadastrando...");
                dlg.show();

                //Cria o novo usuário a partir do Parser
                ParseUser user = new ParseUser();
                user.setUsername(usernameView.getText().toString());
                user.setPassword(passwordView.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            dlg.dismiss();
                            alertDisplayer("Cadastrado com sucesso!");

                        } else {
                            dlg.dismiss();
                            ParseUser.logOut();
                            Toast.makeText(cadastroActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    private boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText text1, EditText text2){
        if(text1.getText().toString().equals(text2.getText().toString())){
            return true;
        }
        else{
            return false;
        }
    }

    private void alertDisplayer(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(cadastroActivity.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}