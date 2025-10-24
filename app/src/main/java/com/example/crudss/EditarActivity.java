package com.example.crudss;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditarActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etEdad;
    private Spinner spinnerTipo;
    private Button btnActualizar, btnCancelar;
    private MascotasController controller;
    private long idMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Configurar Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_mascotas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        // Inicializar controlador
        controller = new MascotasController(this);

        // Obtener datos enviados desde MainActivity
        idMascota = getIntent().getLongExtra("id", -1);
        String nombre = getIntent().getStringExtra("nombre");
        int edad = getIntent().getIntExtra("edad", 0);
        String tipo = getIntent().getStringExtra("tipo");

        // Rellenar campos con datos actuales
        etNombre.setText(nombre);
        etEdad.setText(String.valueOf(edad));

        // Seleccionar el tipo correcto en el Spinner
        if (tipo != null) {
            int spinnerPosition = adapter.getPosition(tipo);
            spinnerTipo.setSelection(spinnerPosition);
        }

        // Botón Actualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarMascota();
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

    private void actualizarMascota() {
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

        // Actualizar mascota
        Mascota mascotaActualizada = new Mascota(nombre, edad, tipo, idMascota);
        int filasAfectadas = controller.guardarCambios(mascotaActualizada);

        if (filasAfectadas > 0) {
            Toast.makeText(this, "Mascota actualizada correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar mascota",
                    Toast.LENGTH_SHORT).show();
        }
    }
}