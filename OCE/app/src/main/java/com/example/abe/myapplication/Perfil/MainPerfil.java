package com.example.abe.myapplication.perfil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.R;
import com.example.abe.myapplication.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
* Classe do perfil "principal". Mostra todos os relatos realizados pelo usuário,
* assim como as opções de edição do perfil. Usamos uma recyclerView para mostrar
* os dados (semelhante ao usado no main).
* Criado por Bruno Bacelar Abe
* */
public class MainPerfil extends AppCompatActivity {

    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;

    private TextView numberShare;
    private TextView textShare;
    private TextView userNamePerfil;

    private RecyclerView recyclerView;
    private PerfilAdapter perfilAdapter;

    private CircleImageView mainPhotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil);

        this.setBarClick();

        numberShare = findViewById(R.id.number_share);
        textShare = findViewById(R.id.text_share);
        userNamePerfil = findViewById(R.id.name_edit_perfil);
        mainPhotoPerfil = findViewById(R.id.imageViewMainPerfil);

        this.putImageToProfile();
        this.getInfoFromParse();

        recyclerView = findViewById(R.id.recycler_view_edit);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    //Coloca a imagem dentro do profile
    public void putImageToProfile(){
        Utils myUtils = new Utils();
        if(ParseUser.getCurrentUser().getParseFile("imagemPerfil") == null){
            mainPhotoPerfil.setImageResource(R.drawable.default_icon);
        }else{
            ParseFile tempPhoto = ParseUser.getCurrentUser().getParseFile("imagemPerfil");
            myUtils.loadImagesCircle(tempPhoto, mainPhotoPerfil);
        }
    }

    //Ações da barra de navegação
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

    //Consulta o banco para pegar algumas informações e chamar o adaptador
    public void getInfoFromParse(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Relato");
        final String objectId =  ParseUser.getCurrentUser().getObjectId();
        query.whereEqualTo("idUser", objectId);
        query.orderByDescending("createdAt");

        textShare.setText(ParseUser.getCurrentUser().getUsername());
        userNamePerfil.setText(ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        numberShare.setText(String.valueOf(objects.size()));

                        perfilAdapter = new PerfilAdapter(objects.size(), objects);
                        recyclerView.setAdapter(perfilAdapter);
                    }
                    if(objects.size() > 1){
                        textShare.setText("Compartilhamentos");
                    }
                }
            }
        });
    }

    //Vai para a classe de edição de perfil
    public void editPerfilOnClick(View view) {
        Intent editPerfil = new Intent(this, EditPerfilActivity.class);
        startActivity(editPerfil);
    }
}
