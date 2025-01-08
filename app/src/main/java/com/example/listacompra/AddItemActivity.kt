package com.example.listacompra

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnAddArticle: Button
    private lateinit var cantidadInput: EditText
    private lateinit var unidadInput: EditText
    private lateinit var nombreInput: EditText
    private lateinit var seccionSpinner: Spinner
    private lateinit var perecederoSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)

        // Inicialización de vistas
        btnBack = findViewById(R.id.btn_back)
        btnAddArticle = findViewById(R.id.btn_add_article)
        cantidadInput = findViewById(R.id.cantidad_input)
        unidadInput = findViewById(R.id.unidad_input)
        nombreInput = findViewById(R.id.nombre_input)
        seccionSpinner = findViewById(R.id.seccion_spinner)
        perecederoSwitch = findViewById(R.id.perecedero_switch)

        // Configuración del Spinner
        val sections = arrayOf("Panadería", "Frutería", "Carnicería", "Charcutería", "General")
        val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_item, sections) // Archivo del ítem seleccionado
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item) // Archivo del desplegable
        seccionSpinner.adapter = spinnerAdapter

        // Botón "Volver"
        btnBack.setOnClickListener {
            finish()
        }

        // Botón "Añadir artículo"
        btnAddArticle.setOnClickListener {
            val cantidad = cantidadInput.text.toString()
            val unidad = unidadInput.text.toString()
            val nombre = nombreInput.text.toString()
            val seccion = seccionSpinner.selectedItem.toString()
            val perecedero = perecederoSwitch.isChecked

            if (cantidad.isNotEmpty() && unidad.isNotEmpty() && nombre.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("item", nombre)
                resultIntent.putExtra("quantity", "$cantidad $unidad")
                resultIntent.putExtra("section", seccion)
                resultIntent.putExtra("perecedero", perecedero)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
