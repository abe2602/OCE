package com.example.abe.myapplication.main;

import android.content.Context;
import android.graphics.Paint;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.R;
import com.example.abe.myapplication.utils.Utils;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.*;

/*
* Adapter da RecyclerView da página principal
* */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
        private int numberItens;
        private List<ParseObject> objectsRecyclerView;
        private double myLat, myLong;

    public int getNumberItens() {
        return numberItens;
    }

    public void setNumberItens(int numberItens) {
        this.numberItens = numberItens;
    }

    public List<ParseObject> getObjectsRecyclerView() {
        return objectsRecyclerView;
    }

    public void setObjectsRecyclerView(List<ParseObject> objectsRecyclerView) {
        this.objectsRecyclerView = objectsRecyclerView;
    }

    public MainAdapter(int numberItens, List<ParseObject> objectsRecyclerView, double myLong, double myLat){
            this.numberItens = numberItens;
            this.objectsRecyclerView = objectsRecyclerView;
            this.myLat = myLat;
            this.myLong = myLong;
        }

    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.item_list_profile;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutId, parent, attachImmediately);
        MainViewHolder viewHolder = new MainViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.numberItens;
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        TextView nameRelato, timeRelato, relato;
        ImageView photoRelato, likeShare, dislikeShare;
        CircleImageView perfilUser;
        double likeCont, dislikeCont;
        int position;

        public MainViewHolder(View itemView) {
            super(itemView);
            nameRelato = itemView.findViewById(R.id.text_name_relato);
            relato = itemView.findViewById(R.id.text_relato);
            timeRelato =  itemView.findViewById(R.id.text_time_relato);
            photoRelato = itemView.findViewById(R.id.fotoRelato);
            perfilUser = itemView.findViewById(R.id.perfilImageView);
            likeShare = itemView.findViewById(R.id.imageLike);
            dislikeShare = itemView.findViewById(R.id.imageDislike);
        }

        /*
        * Coloca as informações dentro da RecyclerView
        * */
        public void bind(final int position){
            this.position = position;
            likeCont = objectsRecyclerView.get(position).getNumber("Likes").doubleValue();
            dislikeCont = objectsRecyclerView.get(position).getNumber("Dislikes").doubleValue();
            final String state = objectsRecyclerView.get(position).getString("Tapped");
            double longitude = objectsRecyclerView.get(position).getNumber("Longitude").doubleValue();
            double latitude = objectsRecyclerView.get(position).getNumber("Latitude").doubleValue();
            final List<String> taps =  objectsRecyclerView.get(position).getList("RelatoRating");

            if(taps.contains(ParseUser.getCurrentUser().getUsername().concat("like"))){
                likeShare.setImageResource(R.drawable.like_blue);
                dislikeShare.setImageResource(R.drawable.dislike);
            }else if(taps.contains(ParseUser.getCurrentUser().getUsername().concat("dislike"))){
                dislikeShare.setImageResource(R.drawable.dislike_blue);
                likeShare.setImageResource(R.drawable.like);
            }


            Log.d("longi", String.valueOf(longitude));

            Utils util = new Utils();
            nameRelato.setText(objectsRecyclerView.get(position).getString("userName"));
            util.findPhoto( objectsRecyclerView.get(position).getString("idUser"), perfilUser);
            objectsRecyclerView.get(position).getDate("updatedAt");

            likeShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> taps =  objectsRecyclerView.get(position).getList("RelatoRating");

                    likeShare.setImageResource(R.drawable.like_blue);
                    dislikeShare.setImageResource(R.drawable.dislike);

                    likeCont += 1;

                    if(dislikeCont > 0)
                        dislikeCont--;

                    if(taps.contains(ParseUser.getCurrentUser().getUsername().concat("dislike"))){
                        int index = taps.indexOf(ParseUser.getCurrentUser().getUsername().concat("dislike"));
                        String remove = taps.get(index);
                        taps.remove(remove);
                        // taps.remove(index);
                    }

                    if(!taps.contains(ParseUser.getCurrentUser().getUsername().concat("like"))){
                        taps.add(ParseUser.getCurrentUser().getUsername().concat("like"));
                    }

                    objectsRecyclerView.get(position).put("Likes", likeCont);
                    objectsRecyclerView.get(position).put("Dislikes", dislikeCont);
                    objectsRecyclerView.get(position).put("RelatoRating", taps);
                    objectsRecyclerView.get(position).saveInBackground();
                }
            });

            dislikeShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> taps2 =  objectsRecyclerView.get(position).getList("RelatoRating");
                    dislikeShare.setImageResource(R.drawable.dislike_blue);
                    likeShare.setImageResource(R.drawable.like);

                    dislikeCont++;

                    if(likeCont > 0)
                        likeCont--;

                    if(taps2.contains(ParseUser.getCurrentUser().getUsername().concat("like"))){
                        int index = taps2.indexOf(ParseUser.getCurrentUser().getUsername().concat("like"));
                        String remove = taps2.get(index);
                        taps2.remove(remove);
                       // taps2.remove(index);
                    }

                    if(!taps2.contains(ParseUser.getCurrentUser().getUsername().concat("dislike"))){
                        taps2.add(ParseUser.getCurrentUser().getUsername().concat("dislike"));

                    }

                    objectsRecyclerView.get(position).put("Likes", likeCont);
                    objectsRecyclerView.get(position).put("Dislikes", dislikeCont);
                    objectsRecyclerView.get(position).put("RelatoRating", taps2);
                    objectsRecyclerView.get(position).saveInBackground();
                }
            });

            String dateString = null;
            SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");

            try{
                dateString = sdfr.format(objectsRecyclerView.get(position).getUpdatedAt());
                if(dateString != null){
                    timeRelato.setText(dateString);
                    Log.d("timeRelat", dateString);
                }
            }catch (Exception ex ){
                Log.d("timeRelat", ex.toString());
            }

            if(objectsRecyclerView.get(position).getString("Categoria").equals("Foto")){
                ParseFile tempPhoto = objectsRecyclerView.get(position).getParseFile("Imagem");
                relato.setText(objectsRecyclerView.get(position).getString("textoImagem"));
                util.loadImages(tempPhoto, photoRelato);
            }else{
                String relateString = objectsRecyclerView.get(position).getString("Categoria") + " " + this.searchAdrress(longitude, latitude);
                relato.setText(relateString);
                double tam = 200;
                Paint paint = new Paint();
                float width;

                switch (objectsRecyclerView.get(position).getString("tipoRelato")) {
                    case "Altura Água na rua":
                        relateString = this.searchAdrress(longitude, latitude) + " está " + objectsRecyclerView.get(position).getString("Categoria");
                        width = paint.measureText(relateString);

                        if(width > tam){
                            relateString = this.searchAdrress(longitude, latitude) + " está \n" + objectsRecyclerView.get(position).getString("Categoria");
                        }

                        relato.setText(relateString);
                        break;
                    case "Intensidade da chuva":
                        relateString = objectsRecyclerView.get(position).getString("Categoria") + " na " + this.searchAdrress(longitude, latitude);
                        width = paint.measureText(relateString);

                        if(width > tam){
                            relateString = objectsRecyclerView.get(position).getString("Categoria") + " na \n" + this.searchAdrress(longitude, latitude);
                        }
                        relato.setText(relateString);
                        break;
                    case "Altura da água no boneco":
                        relateString = objectsRecyclerView.get(position).getString("Categoria") + " na " + this.searchAdrress(longitude, latitude);
                        width = paint.measureText(relateString);

                        if(width > tam){
                            relateString = objectsRecyclerView.get(position).getString("Categoria") + " na \n" + this.searchAdrress(longitude, latitude);
                        }

                        relato.setText(relateString);
                        break;
                    case "Faixa de cores":
                        relateString = objectsRecyclerView.get(position).getString("Categoria") + " na " + this.searchAdrress(longitude, latitude);
                        width = paint.measureText(relateString);

                        if(width > tam){
                            relateString = objectsRecyclerView.get(position).getString("Categoria") + " na \n" + this.searchAdrress(longitude, latitude);
                        }

                        relato.setText(relateString);
                        break;
                    case "Altura da água no leito do rio":
                        relateString = objectsRecyclerView.get(position).getString("Categoria") + " na " + this.searchAdrress(longitude, latitude);
                        width = paint.measureText(relateString);

                        if(width > tam){
                            relateString = objectsRecyclerView.get(position).getString("Categoria") + " na \n" + this.searchAdrress(longitude, latitude);
                        }

                        relato.setText(relateString);
                        break;
                }
            }
        }

        /*
        * Encontra o endereço a partir da longitude e latitude
        * */
        private String searchAdrress(double longi, double lat)  {

            Log.d("longig", String.valueOf(longi));
            Log.d("longig", String.valueOf(lat));

           if(longi != 0 && lat != 0){
               Geocoder geocoder;
               android.location.Address singleAdress = null;
               List<android.location.Address> listAdress;

               geocoder = new Geocoder(itemView.getContext());

               try {
                   listAdress = geocoder.getFromLocation(lat,  longi, 1);

                   if (listAdress.size() > 0){
                       singleAdress = listAdress.get(0);
                       Log.d("searchAdrress", singleAdress.getThoroughfare());

                   }else {
                       Log.d("searchAdrress", "Não encontrado");
                       return " ";
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }

               return singleAdress.getThoroughfare();
           }
           return "temp";
        }

    }
}

