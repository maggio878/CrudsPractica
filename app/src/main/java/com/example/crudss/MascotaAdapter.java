package com.example.crudss;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudss.Mascota;

import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private ArrayList<Mascota> listaMascotas;
    private OnItemClickListener listener;

    // Interface para manejar los clicks
    public interface OnItemClickListener {
        void onEditarClick(Mascota mascota);
        void onEliminarClick(Mascota mascota);
    }

    public MascotaAdapter(ArrayList<Mascota> listaMascotas, OnItemClickListener listener) {
        this.listaMascotas = listaMascotas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mascota, parent, false);
        return new MascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Mascota mascota = listaMascotas.get(position);
        holder.bind(mascota);
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    // Método para actualizar la lista
    public void actualizarLista(ArrayList<Mascota> nuevaLista) {
        this.listaMascotas = nuevaLista;
        notifyDataSetChanged();
    }

    // ViewHolder
    class MascotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEdad;
        Button btnEditar, btnEliminar;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEdad = itemView.findViewById(R.id.tvEdad);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final Mascota mascota) {
            tvNombre.setText(mascota.getNombre());
            tvEdad.setText("Edad: " + mascota.getEdad() + " años");

            // Click en Editar
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditarClick(mascota);
                }
            });

            // Click en Eliminar
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEliminarClick(mascota);
                }
            });
        }
    }
}
