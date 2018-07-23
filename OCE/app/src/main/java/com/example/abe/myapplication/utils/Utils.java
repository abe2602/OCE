package com.example.abe.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.abe.myapplication.R;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
* Classe de métodos uteis
* */
public class Utils {
    //Converte e coloca o parseFile para o CircleImageView
    public void loadImagesCircle(ParseFile thumbnail, final CircleImageView img) {
        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);
                    }
                }
            });
        }
    }

    //Converte e coloca o parseFile para o imageview
    public void loadImages(ParseFile thumbnail, final ImageView img) {
        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);
                    }
                }
            });
        }
    }

    //Coloca a foto de cada usuário na recyclerView
    public void findPhoto(final String idUser, final CircleImageView perfilUser){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", idUser);
        perfilUser.setImageResource(R.drawable.default_icon);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(objects.size() > 0){
                    Utils utils = new Utils();
                    ParseFile tempPhoto = objects.get(0).getParseFile("imagemPerfil");
                    utils.loadImagesCircle(tempPhoto, perfilUser);
                }
            }
        });
    }
}
