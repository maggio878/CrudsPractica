package com.example.crudss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AyudanteBaseDeDatos extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DE_DATOS = "mascotas",
            NOMBRE_TABLA_MASCOTAS = "mascotas";
    private static final int VERSION_BASE_DE_DATOS = 2; // Incrementada la versión

    public AyudanteBaseDeDatos(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, nombre text, edad int, tipo text)", NOMBRE_TABLA_MASCOTAS));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Agregar columna tipo si la base de datos ya existía
            db.execSQL("ALTER TABLE " + NOMBRE_TABLA_MASCOTAS + " ADD COLUMN tipo text DEFAULT 'Perro'");
        }
    }
}