package com.example.listacompra

import android.content.Intent
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var btnClearAll: Button
    private lateinit var btnAdd: Button
    private lateinit var btnPrint: Button
    private lateinit var btnDelete: Button

    private val shoppingList = mutableListOf<String>() // Lista de la compra
    private lateinit var adapter: ShoppingListAdapter // Adaptador personalizado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas
        listView = findViewById(R.id.listViewProvincias)
        btnClearAll = findViewById(R.id.btn_clear_all)
        btnAdd = findViewById(R.id.btn_add)
        btnPrint = findViewById(R.id.btn_print)
        btnDelete = findViewById(R.id.btn_delete)

        // Adaptador del ListView
        adapter = ShoppingListAdapter(shoppingList)
        listView.adapter = adapter

        // Botón "Añadir"
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, 1) // Código de petición 1
        }

        // Botón "Borrar todo"
        btnClearAll.setOnClickListener {
            shoppingList.clear()
            adapter.notifyDataSetChanged()
        }

        // Botón "Borrar"
        btnDelete.setOnClickListener {
            val selectedItems = adapter.getSelectedItemsIndices()
            if (selectedItems.isNotEmpty()) {
                adapter.removeSelectedItems() // Elimina los elementos seleccionados del adaptador
                Toast.makeText(this, "Elementos eliminados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, selecciona elementos para eliminar", Toast.LENGTH_SHORT).show()
            }
        }


        // Botón "Imprimir"
        btnPrint.setOnClickListener {
            val intent = Intent(this, PrintPreviewActivity::class.java)
            intent.putExtra("shoppingList", ArrayList(shoppingList)) // Convertimos la lista a ArrayList para serializar
            startActivity(intent)
        }
    }

    // Recibir datos de AddItemActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val item = data?.getStringExtra("item")
            val quantity = data?.getStringExtra("quantity")
            val section = data?.getStringExtra("section")
            val perecedero = data?.getBooleanExtra("perecedero", false) ?: false

            if (item != null && quantity != null && section != null) {
                shoppingList.add("$item - $quantity - $section - Perecedero: ${if (perecedero) "Sí" else "No"}")
                adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            }
        }
    }

    // Adaptador personalizado
    inner class ShoppingListAdapter(private val items: MutableList<String>) : BaseAdapter() {

        private val selectedItems = mutableSetOf<Int>() // Índices de los elementos seleccionados

        override fun getCount(): Int = items.size

        override fun getItem(position: Int): String = items[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
            val view = convertView ?: layoutInflater.inflate(R.layout.list_item, parent, false)

            val itemName = view.findViewById<TextView>(R.id.item_name)
            val itemDetails = view.findViewById<TextView>(R.id.item_details)
            val itemCheckbox = view.findViewById<CheckBox>(R.id.item_checkbox)

            val data = items[position].split(" - ")

            if (data.size >= 2) {
                itemName.text = data[0]
                itemDetails.text = "${data[1]} - ${data[2]}"
            } else {
                itemName.text = items[position]
                itemDetails.text = ""
            }

            // Configura el estado del CheckBox
            itemCheckbox.setOnCheckedChangeListener(null) // Desactiva temporalmente el listener para evitar bucles
            itemCheckbox.isChecked = selectedItems.contains(position)

            // Configura el listener para el CheckBox
            itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(position)
                } else {
                    selectedItems.remove(position)
                }
            }

            return view
        }

        // Devuelve los índices seleccionados
        fun getSelectedItemsIndices(): List<Int> = selectedItems.toList()

        // Elimina los elementos seleccionados
        fun removeSelectedItems() {
            val indicesToRemove = selectedItems.sortedDescending() // Orden inverso para evitar errores de índice
            for (index in indicesToRemove) {
                items.removeAt(index)
            }
            selectedItems.clear()
            notifyDataSetChanged()
        }
    }


}
