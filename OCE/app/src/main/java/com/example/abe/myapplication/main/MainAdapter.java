package com.example.abe.myapplication.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abe.myapplication.R;
import com.parse.ParseObject;

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

        public MainViewHolder(View itemView) {
            super(itemView);
            nameRelato = (TextView) itemView.findViewById(R.id.text_name_relato);
            relato = (TextView) itemView.findViewById(R.id.text_relato);
            timeRelato = (TextView) itemView.findViewById(R.id.text_time_relato);
        }

        public void bind(int position){
            nameRelato.setText(objectsRecyclerView.get(position).getString("userName"));
            relato.setText(objectsRecyclerView.get(position).getString("Categoria"));
        }
    }
}

