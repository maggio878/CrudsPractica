package com.example.crudss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MascotasController {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "mascotas";

    public MascotasController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }

    public int eliminarMascota(Mascota mascota) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(mascota.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaMascota(Mascota mascota) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", mascota.getNombre());
        valoresParaInsertar.put("edad", mascota.getEdad());
        valoresParaInsertar.put("tipo", mascota.getTipo());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Mascota mascotaEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", mascotaEditada.getNombre());
        valoresParaActualizar.put("edad", mascotaEditada.getEdad());
        valoresParaActualizar.put("tipo", mascotaEditada.getTipo());
        String campoParaActualizar = "id = ?";
        String[] argumentosParaActualizar = {String.valueOf(mascotaEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Mascota> obtenerMascotas() {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        String[] columnasAConsultar = {"nombre", "edad", "tipo", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            return mascotas;
        }

        if (!cursor.moveToFirst()) return mascotas;

        do {
            String nombreObtenidoDeBD = cursor.getString(0);
            int edadObtenidaDeBD = cursor.getInt(1);
            String tipoObtenidoDeBD = cursor.getString(2);
            long idMascota = cursor.getLong(3);
            Mascota mascotaObtenidaDeBD = new Mascota(nombreObtenidoDeBD, edadObtenidaDeBD, tipoObtenidoDeBD, idMascota);
            mascotas.add(mascotaObtenidaDeBD);
        } while (cursor.moveToNext());

        cursor.close();
        return mascotas;
    }
}