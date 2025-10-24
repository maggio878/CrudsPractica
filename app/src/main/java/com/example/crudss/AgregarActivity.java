package com.example.crudss;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AgregarActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etEdad;
    private Spinner spinnerTipo;
    private Button btnGuardar, btnCancelar;
    private MascotasController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Configurar Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_mascotas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        // Inicializar controlador
        controller = new MascotasController(this);

        // Botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarMascota();
            }
        });

        // Botón Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void guardarMascota() {
        String nombre = etNombre.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String tipo = spinnerTipo.getSelectedItem().toString();

        // Validaciones
        if (nombre.isEmpty()) {
            etNombre.setError("Ingresa el nombre");
            etNombre.requestFocus();
            return;
        }

        if (edadStr.isEmpty()) {
            etEdad.setError("Ingresa la edad");
            etEdad.requestFocus();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 0) {
                etEdad.setError("La edad debe ser positiva");
                etEdad.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etEdad.setError("Ingresa un número válido");
            etEdad.requestFocus();
            return;
        }

        // Crear y guardar mascota
        Mascota nuevaMascota = new Mascota(nombre, edad, tipo);
        long id = controller.nuevaMascota(nuevaMascota);

        if (id > 0) {
            Toast.makeText(this, "Mascota agregada correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al agregar mascota",
                    Toast.LENGTH_SHORT).show();
        }
    }
}