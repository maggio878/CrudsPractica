package com.example.crudss;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private ArrayList<Mascota> listaMascotas;
    private OnItemClickListener listener;

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

    public void actualizarLista(ArrayList<Mascota> nuevaLista) {
        this.listaMascotas = nuevaLista;
        notifyDataSetChanged();
    }

    class MascotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEdad, tvTipo, tvIcono;
        Button btnEditar, btnEliminar;
        CardView cardView;
        View colorBar;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEdad = itemView.findViewById(R.id.tvEdad);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvIcono = itemView.findViewById(R.id.tvIcono);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            cardView = itemView.findViewById(R.id.cardMascota);
            colorBar = itemView.findViewById(R.id.colorBar);
        }

        public void bind(final Mascota mascota) {
            tvNombre.setText(mascota.getNombre());
            tvEdad.setText(getEdadTexto(mascota.getEdad()));
            tvTipo.setText(mascota.getTipo());

            // Configurar icono y color según tipo
            configurarIconoYColor(mascota.getTipo());

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditarClick(mascota);
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEliminarClick(mascota);
                }
            });
        }

        private String getEdadTexto(int edad) {
            if (edad == 1) {
                return edad + " año";
            } else {
                return edad + " años";
            }
        }

        private void configurarIconoYColor(String tipo) {
            int color;
            String icono;

            switch (tipo) {
                case "Perro":
                    icono = "🐕";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.perro);
                    break;
                case "Gato":
                    icono = "🐈";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.gato);
                    break;
                case "Ave":
                    icono = "🦜";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.ave);
                    break;
                case "Pez":
                    icono = "🐠";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.pez);
                    break;
                case "Hámster":
                    icono = "🐹";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.hamster);
                    break;
                case "Conejo":
                    icono = "🐰";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.conejo);
                    break;
                default:
                    icono = "🐾";
                    color = ContextCompat.getColor(itemView.getContext(), R.color.otro);
                    break;
            }

            tvIcono.setText(icono);
            colorBar.setBackgroundColor(color);
        }
    }
}