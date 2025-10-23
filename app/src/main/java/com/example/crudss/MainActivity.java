package com.example.crudss;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.crudss.MascotasController;
import com.example.crudss.Mascota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MascotaAdapter adapter;
    private MascotasController controller;
    private ArrayList<Mascota> listaMascotas;
    private FloatingActionButton fabAgregar;
    private TextView tvVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewMascotas);
        fabAgregar = findViewById(R.id.fabAgregar);
        tvVacio = findViewById(R.id.tvVacio);

        // Inicializar controlador
        controller = new MascotasController(this);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaMascotas = new ArrayList<>();

        // Configurar adaptador con listener
        adapter = new MascotaAdapter(listaMascotas, new MascotaAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(Mascota mascota) {
                abrirEditarActivity(mascota);
            }

            @Override
            public void onEliminarClick(Mascota mascota) {
                mostrarDialogoEliminar(mascota);
            }
        });

        recyclerView.setAdapter(adapter);

        // Botón flotante para agregar
        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarActivity.class);
                startActivity(intent);
            }
        });

        // Cargar mascotas
        cargarMascotas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar cuando volvemos a esta pantalla
        cargarMascotas();
    }

    private void cargarMascotas() {
        listaMascotas = controller.obtenerMascotas();
        adapter.actualizarLista(listaMascotas);

        // Mostrar mensaje si está vacío
        if (listaMascotas.isEmpty()) {
            tvVacio.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvVacio.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void abrirEditarActivity(Mascota mascota) {
        Intent intent = new Intent(MainActivity.this, EditarActivity.class);
        intent.putExtra("id", mascota.getId());
        intent.putExtra("nombre", mascota.getNombre());
        intent.putExtra("edad", mascota.getEdad());
        startActivity(intent);
    }

    private void mostrarDialogoEliminar(final Mascota mascota) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de eliminar a " + mascota.getNombre() + "?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long resultado = controller.eliminarMascota(mascota);
                        if (resultado > 0) {
                            Toast.makeText(MainActivity.this,
                                    "Mascota eliminada", Toast.LENGTH_SHORT).show();
                            cargarMascotas();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}