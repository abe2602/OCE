package com.example.abe.myapplication.perfil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abe.myapplication.R;


/*A ideia aqui é, em cada posição da recyclerView, usar um novo layout*/
public class EditPerfilAdapter extends  RecyclerView.Adapter<EditPerfilAdapter.EditPerfilViewHolder>{
    //Itens que serão usados na RecyclerView
    private int[] items;
    private final int PHOTO = 0, NAME = 1, GPS = 2, RADIUS = 3, MAP = 4;

    public EditPerfilAdapter(int[] items){
        this.items = items;
    }

    @NonNull
    @Override
    public EditPerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditPerfilViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater;
        int layoutId;
        View view;

        switch (viewType) { //arrumar os layouts
            case 0:
                layoutId = R.layout.edit_profile_photo; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 1:
                layoutId = R.layout.edit_profile_name; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 2:
                layoutId = R.layout.edit_profile_gps; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 3:
                layoutId = R.layout.edit_profile_range; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 4:
                layoutId = R.layout.edit_profile_map; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            default:
                layoutId = R.layout.edit_profile_name; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditPerfilViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return this.items.length;
    }

    //Escolhe o tipo do layout
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return PHOTO;
        }else if(position == 1){
            return NAME;
        }else if(position == 2){
            return GPS;
        }else if(position == 3){
            return RADIUS;
        }else if(position == 4){
            return MAP;
        }
        return -1;
    }

    class EditPerfilViewHolder extends RecyclerView.ViewHolder{
        public EditPerfilViewHolder(View itemView) {
            super(itemView);

        }

        public void bind(){

        }
    }
}
