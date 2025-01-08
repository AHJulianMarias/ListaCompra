package com.example.listacompra

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrintPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_preview)

        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        val btnPrint = findViewById<Button>(R.id.btn_print)

        // Recuperar la lista de productos enviada desde MainActivity
        val shoppingList = intent.getStringArrayListExtra("shoppingList")
        val listContainer = findViewById<LinearLayout>(R.id.list_container)

        shoppingList?.forEach { itemString ->
            // Divide el string en sus componentes
            val parts = itemString.split(" - ")
            val itemName = parts[0] // Nombre del producto
            val itemQuantity = parts[1] // Cantidad
            val itemSection = parts[2] // Sección
            val itemPerecedero = parts[3] // Perecedero o no

            // Crear un nuevo elemento de vista para cada producto
            val itemView = layoutInflater.inflate(R.layout.print_list_item, listContainer, false)

            val itemNameSection = itemView.findViewById<TextView>(R.id.item_name_section)
            val itemQuantityView = itemView.findViewById<TextView>(R.id.item_quantity)
            val itemPerecederoView = itemView.findViewById<TextView>(R.id.item_perecedero)

            // Configurar los valores en las vistas
            itemNameSection.text = "$itemName - $itemSection"
            itemQuantityView.text = itemQuantity
            itemPerecederoView.text = itemPerecedero

            // Añadir el elemento al contenedor de la lista
            listContainer.addView(itemView)
        }

        // Botón de volver
        btnBack.setOnClickListener {
            finish()
        }

        // Botón de imprimir (mostrar mensaje de funcionalidad no habilitada)
        btnPrint.setOnClickListener {
            Toast.makeText(this, "Función de impresión no habilitada", Toast.LENGTH_SHORT).show()
        }
    }
}
