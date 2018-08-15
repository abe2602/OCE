package com.example.abe.myapplication.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.example.abe.myapplication.R;
import com.example.abe.myapplication.login.Login;
import com.example.abe.myapplication.utils.Utils;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
* Adapter da RecyclerView da página principal
* */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
        private int numberItens;
        private List<ParseObject> objectsRecyclerView;

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

    public MainAdapter(int numberItens, List<ParseObject> objectsRecyclerView ){
            this.numberItens = numberItens;
            this.objectsRecyclerView = objectsRecyclerView;
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
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.numberItens;
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        TextView nameRelato, timeRelato, relato;
        ImageView photoRelato;
        CircleImageView perfilUser;

        public MainViewHolder(View itemView) {
            super(itemView);
            nameRelato = itemView.findViewById(R.id.text_name_relato);
            relato = itemView.findViewById(R.id.text_relato);
            timeRelato =  itemView.findViewById(R.id.text_time_relato);
            photoRelato = itemView.findViewById(R.id.fotoRelato);
            perfilUser = itemView.findViewById(R.id.perfilImageView);
        }

        /*
        * Coloca as informações dentro da RecyclerView
        * */
        public void bind(int position){
            Utils util = new Utils();
            nameRelato.setText(objectsRecyclerView.get(position).getString("userName"));
            util.findPhoto( objectsRecyclerView.get(position).getString("idUser"), perfilUser);

            if(objectsRecyclerView.get(position).getString("Categoria").equals("Foto")){
                ParseFile tempPhoto = objectsRecyclerView.get(position).getParseFile("Imagem");
                relato.setText(objectsRecyclerView.get(position).getString("textoImagem"));
                util.loadImages(tempPhoto, photoRelato);
            }else{
                double longitude = objectsRecyclerView.get(position).getNumber("Longitude").doubleValue();
                double latitude = objectsRecyclerView.get(position).getNumber("Latitude").doubleValue();
                String relateString = objectsRecyclerView.get(position).getString("Categoria") + " " + this.searchAdrress(longitude, latitude);
                relato.setText(relateString);
                double tam = 200;//relato.getMeasuredWidth();
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

        public String searchAdrress(double longi, double lat)  {

            Geocoder geocoder;
            android.location.Address singleAdress = null;
            List<android.location.Address> listAdress;

            geocoder = new Geocoder(itemView.getContext());

            try {
                listAdress = geocoder.getFromLocation(lat, longi, 1);

                if (listAdress.size() > 0){
                    singleAdress = listAdress.get(0);
                    Log.d("end", singleAdress.getThoroughfare());

                }else {
                    Log.d("end", "deu ruim");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return singleAdress.getThoroughfare();
        }

    }
}

