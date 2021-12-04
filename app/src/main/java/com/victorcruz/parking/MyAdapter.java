package com.victorcruz.parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ParqueosPojo> listaParqueos;

    public MyAdapter(Context context, ArrayList<ParqueosPojo> listaParqueos) {
        this.context = context;
        this.listaParqueos = listaParqueos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_parqueo, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ParqueosPojo datosParqueo = listaParqueos.get(position);
            holder.nombreParqueo.setText(datosParqueo.getNombreParqueo());
            holder.disponible.setText(datosParqueo.getDisponible());
    }

    @Override
    public int getItemCount() {
        return listaParqueos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombreParqueo, disponible;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreParqueo = itemView.findViewById(R.id.tv_nombre_parqueo);
            disponible = itemView.findViewById(R.id.tv_disponibles);

        }
    }
}
