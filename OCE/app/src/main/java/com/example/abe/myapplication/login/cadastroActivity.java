package com.example.abe.myapplication.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abe.myapplication.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import java.io.ByteArrayOutputStream;

/*
 * Classe de cadastro.
 *
 * Criado por Bruno Bacelar Abe
 * */
public class cadastroActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private EditText passwordAgainView;
    private EditText telephoneView;
    private EditText cityView;
    private EditText emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Parse.initialize(this);

        usernameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        emailView = findViewById(R.id.email);
        passwordAgainView =  findViewById(R.id.passwordAgain);
        telephoneView = findViewById(R.id.telephoneEditText);
        cityView = findViewById(R.id.cityEditText);
        final Intent intent = new Intent(this, Login.class);
        /*
        * Função do botão
        * */
        final Button cancel_button = findViewById(R.id.button_cancel2);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final Button signup_button = findViewById(R.id.button_save2);
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
                if (isEmpty(telephoneView)) {
                    if (validationError) {
                        validationErrorMessage.append(" e ");
                    }
                    validationError = true;
                    validationErrorMessage.append("telefone vazio");
                }
                if (isEmpty(cityView)) {
                    if (validationError) {
                        validationErrorMessage.append(" e ");
                    }
                    validationError = true;
                    validationErrorMessage.append("campo vazio");
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

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_icon);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();
                ParseFile photoRelato  = new ParseFile("imagem.png", bitmapdata); //Parse só aceita byteArray

                //Cria o novo usuário a partir do Parser
                final ParseUser user = new ParseUser();
                user.setUsername(usernameView.getText().toString());
                user.setPassword(passwordView.getText().toString());
                user.setEmail(emailView.getText().toString());
                user.put("raioInteresse", 10);
                user.put("Login", emailView.getText().toString());
                user.put("Cidade", cityView.getText().toString());
                user.put("Telefone", Integer.valueOf(telephoneView.getText().toString()));
                user.put("Reputacao", 0);
                user.put("Longitude", 0);
                user.put("Latitude", 0);
                user.put("gpsEnable", true);

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

    //Verifica se o campo está vazio
    private boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    //Verifica se os dois textos de senha estão batendo
    private boolean isMatching(EditText text1, EditText text2){
        if(text1.getText().toString().equals(text2.getText().toString())){
            return true;
        }
        else{
            return false;
        }
    }

    //Cria um alerta
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