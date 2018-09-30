package com.example.abe.myapplication.perfil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.R;
import com.example.abe.myapplication.utils.Utils;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*Adaptador da RecyclerView.
* É aqui onde os dados são colocados na RecyclerView. Para tal tarefa, criamos uma
* ViewHolder que fará o bind dos dados oriundos do back4app (Parse)
* Criado por Bruno Bacelar Abe
* */
public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder> {
    private int numberItens;
    private List<ParseObject> objectsRecyclerView;

    public PerfilAdapter(int numberItens,List<ParseObject> objectsRecyclerView ){
        this.numberItens = numberItens;
        this.objectsRecyclerView = objectsRecyclerView;
    }

    @Override
    public PerfilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.item_list_profile;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutId, parent, attachImmediately);
        PerfilViewHolder viewHolder = new PerfilViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PerfilViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.numberItens;
    }

    class PerfilViewHolder extends RecyclerView.ViewHolder{
        TextView nameRelato, timeRelato, relato;
        ImageView photoRelato, likeShare, dislikeShare;
        CircleImageView perfilUser;

        public PerfilViewHolder(View itemView) {
            super(itemView);
            nameRelato = itemView.findViewById(R.id.text_name_relato);
            relato = itemView.findViewById(R.id.text_relato);
            timeRelato =  itemView.findViewById(R.id.text_time_relato);
            photoRelato =  itemView.findViewById(R.id.fotoRelato);
            perfilUser = itemView.findViewById(R.id.perfilImageView);
            likeShare = itemView.findViewById(R.id.imageLike);
            dislikeShare = itemView.findViewById(R.id.imageDislike);
        }

        /*
         * Coloca as informações dentro da RecyclerView
         * */
        public void bind(int position){
            String state = objectsRecyclerView.get(position).getString("Tapped");
            double longitude = objectsRecyclerView.get(position).getNumber("Longitude").doubleValue();
            double latitude = objectsRecyclerView.get(position).getNumber("Latitude").doubleValue();
            List<String> taps =  objectsRecyclerView.get(position).getList("RelatoRating");

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
         * Busca o endereço a partir da latitude e longitude
         * */
        @SuppressLint("Assert")
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
                        Log.d("end", singleAdress.getThoroughfare());
                    }else {
                        Log.d("end", "deu ruim");
                        return " ";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return " ";
                }

                assert false;
                return singleAdress.getThoroughfare();
            }
            return "temp";
        }
    }
}
