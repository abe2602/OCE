package com.example.abe.mobiletrabalho.mic;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.mobiletrabalho.Data.ImageClass;
import com.example.abe.mobiletrabalho.Data.imageDatabase;
import com.example.abe.mobiletrabalho.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MicActivity extends AppCompatActivity {
    private TextView showText;
    private ImageView showPhoto;
    private ImageClass choosen;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic);
        imageDatabase databaseMic = imageDatabase.getDatabase(getApplicationContext());

        showText = (TextView) findViewById(R.id.auxTextView);
        showPhoto = (ImageView) findViewById(R.id.mic_image_view);

        this.populateDataBase();

        List<ImageClass> imageList = databaseMic.imageDao().getAllImagesByType("animal");
        Random imagePosition = new Random();
        int position = imagePosition.nextInt(imageList.size());

        Log.d("Position", String.valueOf(position));

        String str = "animal_image" + String.valueOf(position);

        setChoosen(imageList.get(position));

        showPhoto.setImageDrawable(
                getResources().getDrawable(getResourceID(str, "drawable", getApplicationContext())));

        String talkedString = getChoosen().getDescription();
        Log.d("animal ", talkedString);
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }

    public ImageClass getChoosen() {
        return choosen;
    }

    public void setChoosen(ImageClass choosen) {
        this.choosen = choosen;
    }

    public void populateDataBase (){
        imageDatabase databaseMic = imageDatabase.getDatabase(getApplicationContext());
        databaseMic.imageDao().deleteAll();
        ImageClass insertedImage = new ImageClass("","","");

        insertedImage.setType("animal");
        insertedImage.setDescription("aguia");
        insertedImage.setName("animal_image0");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("camelo");
        insertedImage.setName("animal_image1");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("cachorro");
        insertedImage.setName("animal_image2");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("elefante");
        insertedImage.setName("animal_image3");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("raposa");
        insertedImage.setName("animal_image4");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("flamingo");
        insertedImage.setName("animal_image5");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("cegonha");
        insertedImage.setName("animal_image6");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("cavalo");
        insertedImage.setName("animal_image7");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("canguru");
        insertedImage.setName("animal_image8");
        databaseMic.imageDao().addImage(insertedImage);

        insertedImage.setType("animal");
        insertedImage.setDescription("coruja");
        insertedImage.setName("animal_image9");
        databaseMic.imageDao().addImage(insertedImage);
    }

    private void openMic(){
        Intent intentMic = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //Cria uma nova intent para ouvirr
        intentMic.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); //Qual tipo de linguagem será ouvida

        intentMic.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); //Pega a língua padrão do telefone
        intentMic.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale agora!!!"); //Abre o microfone do telefone

        try{
            startActivityForResult(intentMic, REQ_CODE_SPEECH_OUTPUT);

        }catch (Exception e){
            Log.i("Deu ruim", "Microfone morreu");
        }
    }

    public void listenOnClick(View view) {
       openMic();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_OUTPUT:{
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> voiceIntent = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //Pega os textos
                    //showText.setText(voiceIntent.get(0)); //Coloca o texto na textView

                    String talkedString = getChoosen().getDescription();

                  //  Log.d("HUEHUEHUEUHEHUHEU ", talkedString);
                    String micString = Normalizer.normalize(voiceIntent.get(0), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]",  "");
                    String minString = micString.toLowerCase();

                    Log.d("HUEHUEHUEUHEHUHEU ", minString);
                    Log.d("HUEHUEHUEUHEHUHEU ", talkedString);

                    if(talkedString.equals(minString)){
                        showText.setText("ACERTOU!"); //Coloca o texto na textView
                    }
                }
            }
            break;
        }
    }

    /*Gero o ID das imagens a partir do número randômico.*/
    protected final static int getResourceID (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }

}
