package com.example.abe.myapplication.perfil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abe.myapplication.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/*Adaptador da RecyclerView.
* É aqui onde os dados são colocados na RecyclerView. Para tal tarefa, criamos uma
* ViewHolder que fará o bind dos dados oriundos do back4app (Parse)*/
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
        ImageView photoRelato;

        public PerfilViewHolder(View itemView) {
            super(itemView);
            nameRelato = (TextView) itemView.findViewById(R.id.text_name_relato);
            relato = (TextView) itemView.findViewById(R.id.text_relato);
            timeRelato = (TextView) itemView.findViewById(R.id.text_time_relato);
            photoRelato = (ImageView) itemView.findViewById(R.id.fotoRelato);

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
