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

import java.io.IOException;
import java.text.SimpleDateFormat;
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

    //Fazer o Switch com dois layouts, um vazio e o outro normal
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        double longitude = objectsRecyclerView.get(position).getNumber("Longitude").doubleValue();
        double latitude = objectsRecyclerView.get(position).getNumber("Latitude").doubleValue();
        double raio = (int)ParseUser.getCurrentUser().getNumber("raioInteresse");
        double dist = findDistanceLat(latitude, longitude, myLat, myLong);
        holder.bind(position);

        if(dist <= raio){
      //    holder.bind(position);
        }else{
          // holder.bind(0);
        }
    }

    private double findDistanceLat(double lat_inicial, double long_inicial, double lat_final, double long_final){
        double d2r = 0.017453292519943295769236;
        double dlong = (long_final - long_inicial) * d2r;
        double dlat = (lat_final - lat_inicial) * d2r;

        double temp_sin = sin(dlat/2.0);
        double temp_cos = cos(lat_inicial * d2r);
        double temp_sin2 = sin(dlong/2.0);

        double a = (temp_sin * temp_sin) + (temp_cos * temp_cos) * (temp_sin2 * temp_sin2);
        double c = 2.0 * atan2(sqrt(a), sqrt(1.0 - a));

        return 6368.1 * c;
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
        *
        * NÃO SEI SE VAI FUNCIONAR, MAS A IDEIA É COLOCAR UM NOVO XML PARA OS RELATOS QUE NÃO ENTRAM NO IF
        * ESSE XML VAI ESTAR VAZIO
        * */
        public void bind(int position){

            double longitude = objectsRecyclerView.get(position).getNumber("Longitude").doubleValue();
            double latitude = objectsRecyclerView.get(position).getNumber("Latitude").doubleValue();

            Log.d("longi", String.valueOf(longitude));

            Utils util = new Utils();
            nameRelato.setText(objectsRecyclerView.get(position).getString("userName"));
            util.findPhoto( objectsRecyclerView.get(position).getString("idUser"), perfilUser);
            objectsRecyclerView.get(position).getDate("updatedAt");

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
        * BUGOU AQUI POR ALGUM FUCKING MOTIVO, INVESTIGAR
        * */
        public String searchAdrress(double longi, double lat)  {
            Geocoder geocoder;
            android.location.Address singleAdress = null;
            List<android.location.Address> listAdress;

            Log.d("longig", String.valueOf(longi));
            Log.d("longig", String.valueOf(lat));

            geocoder = new Geocoder(itemView.getContext());

            try {
                listAdress = geocoder.getFromLocation(lat, longi, 1);

                if (listAdress.size() > 0){
                    singleAdress = listAdress.get(0);
                    Log.d("end", singleAdress.getThoroughfare());

                }else {
                    Log.d("end", "deu ruim");
                    return "deu ruim";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

                return singleAdress.getThoroughfare();
        }

    }
}

