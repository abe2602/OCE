package com.example.abe.mobiletrabalho.danger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abe.mobiletrabalho.R;

public class DangerAdapter extends RecyclerView.Adapter<DangerAdapter.DangerViewHolder> {
    private int numberItens;

    public DangerAdapter(int numberItens){
        this.numberItens = numberItens;
    }

    @Override
    public DangerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.list_itens;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutId, parent, attachImmediately);
        DangerViewHolder viewHolder = new DangerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DangerViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return this.numberItens;
    }

    class DangerViewHolder extends RecyclerView.ViewHolder{
        TextView listItem;

        public DangerViewHolder(View itemView) {
            super(itemView);
            listItem = (TextView) itemView.findViewById(R.id.itens_list);
        }

        public void bind(){
            listItem.setText("Guará Viado");
        }
    }
}
