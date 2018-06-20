package com.example.abe.myapplication.perfil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abe.myapplication.R;
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

        public PerfilViewHolder(View itemView) {
            super(itemView);
            nameRelato = (TextView) itemView.findViewById(R.id.text_name_relato);
            relato = (TextView) itemView.findViewById(R.id.text_relato);
            timeRelato = (TextView) itemView.findViewById(R.id.text_time_relato);
        }

        public void bind(int position){
            nameRelato.setText(ParseUser.getCurrentUser().getUsername());
            relato.setText( objectsRecyclerView.get(position).getString("Categoria"));
        }
    }
}
