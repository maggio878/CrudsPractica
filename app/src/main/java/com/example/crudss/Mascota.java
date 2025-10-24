package com.example.crudss;

public class Mascota {

    private String nombre;
    private int edad;
    private String tipo; // Nuevo campo
    private long id;

    public Mascota(String nombre, int edad, String tipo) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
    }

    // Constructor para cuando instanciamos desde la BD
    public Mascota(String nombre, int edad, String tipo, long id) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}