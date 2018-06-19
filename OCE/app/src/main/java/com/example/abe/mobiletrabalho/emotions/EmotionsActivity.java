package com.example.abe.mobiletrabalho.emotions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.abe.mobiletrabalho.Data.ImageClass;
import com.example.abe.mobiletrabalho.Data.imageDatabase;
import com.example.abe.mobiletrabalho.R;

import java.util.List;
import java.util.Random;

/*A ideia aqui é usar as emoções de Divertida mente,
dentro dos botões.!!!*/
public class EmotionsActivity extends AppCompatActivity {
    private String rightEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions);

        imageDatabase database = imageDatabase.getDatabase(getApplicationContext());
        ImageView imageViewEmotions;

        imageViewEmotions = (ImageView)findViewById(R.id.emotion_image_view);

        this.populateDataBase();

        Random imagePosition = new Random();
        int position = imagePosition.nextInt(15);
        List<ImageClass> imageList = database.imageDao().getAllImagesByType("emotion");

        final String str = "emotion_image"+position;

        Log.d("Emotion", imageList.get(position).getDescription());

        imageViewEmotions.setImageDrawable(
                        getResources().getDrawable(getResourceID(str, "drawable", getApplicationContext())));

    }

    public void populateDataBase(){
        imageDatabase database = imageDatabase.getDatabase(getApplicationContext());

        database.imageDao().deleteAll();
        ImageClass insertedImage = new ImageClass("animal_image2","alegria","emotion");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("alegria");
        insertedImage.setName("emotion_image1");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("alegria");
        insertedImage.setName("emotion_image3");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("medo");
        insertedImage.setName("emotion_image4");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("medo");
        insertedImage.setName("emotion_image5");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("nojo");
        insertedImage.setName("emotion_image6");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("nojo");
        insertedImage.setName("emotion_image7");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("nojo");
        insertedImage.setName("emotion_image8");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("raiva");
        insertedImage.setName("emotion_image9");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("raiva");
        insertedImage.setName("emotion_image10");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("raiva");
        insertedImage.setName("emotion_image11");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("tristeza");
        insertedImage.setName("emotion_image12");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("tristeza");
        insertedImage.setName("emotion_image13");
        database.imageDao().addImage(insertedImage);

        insertedImage.setType("emotion");
        insertedImage.setDescription("tristeza");
        insertedImage.setName("emotion_image14");
        database.imageDao().addImage(insertedImage);
    }

    public void checkRaiva(View view) {
    }

    public void checkTristeza(View view) {
    }

    public void checkFelicidade(View view) {
    }

    public void checkNojo(View view) {
    }

    public void checkMedo(View view) {
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
