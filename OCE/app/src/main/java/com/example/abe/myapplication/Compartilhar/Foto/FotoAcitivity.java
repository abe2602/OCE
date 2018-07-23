package com.example.abe.myapplication.compartilhar.foto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.abe.myapplication.compartilhar.MainCompartilhamento;
import com.example.abe.myapplication.main.MainActivity;
import com.example.abe.myapplication.perfil.MainPerfil;
import com.example.abe.myapplication.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class FotoAcitivity extends AppCompatActivity {
    private Intent intentProfile;
    private Intent intentShare;
    private Intent intentMain;
    private ImageView showPhoto;
    private Bitmap imageBitmap;
    private EditText comentPhoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final int SDCARD_REQUEST_CODE = 1235;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_acitivity);
        this.setBarClick();

        showPhoto = findViewById(R.id.imageView);
        comentPhoto = findViewById(R.id.editTextComent);

        this.dispatchTakePictureIntent();
    }

    /*
    * Tira a foto de fato
    * */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, SDCARD_REQUEST_CODE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /*
    * O que fazer depois que a foto é tirada
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { //caso tudo tenha dado certo
            Bundle extras = data.getExtras();
            this.imageBitmap = (Bitmap) extras.get("data"); //bitmap da imagem
            showPhoto.setImageBitmap(imageBitmap); //coloca a imagem na ImageView
        }
    }


    public void setBarClick(){
        ImageView imageProfile = findViewById(R.id.imageProfile);
        this.intentProfile = new Intent(this, MainPerfil.class);

        ImageView imageMain = findViewById(R.id.imageHome);
        this.intentMain = new Intent(this, MainActivity.class);

        ImageView imageShare = findViewById(R.id.imageShare);
        this.intentShare = new Intent(this, MainCompartilhamento.class);

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

    public void sendRelato(String categoria, String tipo){
        ParseObject relato = new ParseObject("Relato");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        relato.put("idUser", ParseUser.getCurrentUser().getObjectId());
        relato.put("userName", ParseUser.getCurrentUser().getUsername());
        relato.put("textoImagem", tipo);
        relato.put("Categoria", categoria);

        imageBitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);
        byte[] relatoPhotoByteArray = stream.toByteArray();
        ParseFile photoRelato  = new ParseFile("imagem.png", relatoPhotoByteArray); //Parse só aceita byteArray
        relato.put("Imagem", photoRelato);

        /*
        *
        * FALTA
        * - Pegar as coordenadas do GPS
        * - Achar a rua pelo GPS
        * */

        relato.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("Foto", "deu bom");
                    finish();
                    Intent intent = new Intent(FotoAcitivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Log.i("Foto", "ddeu ruim");
                }
            }
        });
    }

    public void cancelPhoto(View view) {
    }

    public void savePhoto(View view) {
        this.sendRelato("Foto", this.comentPhoto.getText().toString());
    }
}
