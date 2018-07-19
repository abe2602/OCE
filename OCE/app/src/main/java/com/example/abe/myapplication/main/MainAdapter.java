package com.example.abe.myapplication.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.R;
import com.example.abe.myapplication.login.Login;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
        private int numberItens;
        private List<ParseObject> objectsRecyclerView;

        public MainAdapter(int numberItens,List<ParseObject> objectsRecyclerView ){
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
        ImageView photoRelato, perfilUser;

        public MainViewHolder(View itemView) {
            super(itemView);
            nameRelato = itemView.findViewById(R.id.text_name_relato);
            relato = itemView.findViewById(R.id.text_relato);
            timeRelato =  itemView.findViewById(R.id.text_time_relato);
            photoRelato = itemView.findViewById(R.id.fotoRelato);
            perfilUser = itemView.findViewById(R.id.perfilImageView);
        }

        public void bind(int position){
             //   relato.setText(objectsRecyclerView.get(position).getString("textoImagem"));
            nameRelato.setText(objectsRecyclerView.get(position).getString("userName"));
            Log.d("catigoria", objectsRecyclerView.get(position).getString("Categoria"));

            if(objectsRecyclerView.get(position).getString("Categoria").equals("Foto")){
                ParseFile tempPhoto = objectsRecyclerView.get(position).getParseFile("Imagem");
                relato.setText(objectsRecyclerView.get(position).getString("textoImagem"));
                loadImages(tempPhoto, photoRelato);
            }else{
                relato.setText(objectsRecyclerView.get(position).getString("Categoria"));
              //  Log.d("imghue",  ParseUser.getCurrentUser().get("imagemPerfil").toString());
            }
        }

        private void loadImages(ParseFile thumbnail, final ImageView img) {

            if (thumbnail != null) {
                thumbnail.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            img.setImageBitmap(bmp);
                        } else {
                        }
                    }
                });
            } else {
               // img.setImageResource();
            }
        }// load image
    }
}

