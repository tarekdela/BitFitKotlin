package com.example.bitfit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.data.WaterEntry
import com.example.bitfit.ui.WaterAdapter
import com.example.bitfit.viewmodel.WaterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WaterViewModel
    private lateinit var adapter: WaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[WaterViewModel::class.java]

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = WaterAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe data changes
        viewModel.allEntries.observe(this) { entries ->
            entries?.let { adapter.submitList(it) }
        }

        // Set up add button
        val editTextAmount = findViewById<EditText>(R.id.et_amount)
        val buttonAdd = findViewById<Button>(R.id.btn_add)

        buttonAdd.setOnClickListener {
            val amountText = editTextAmount.text.toString()

            if (amountText.isNotEmpty()) {
                try {
                    val amount = amountText.toInt()
                    if (amount > 0) {
                        val entry = WaterEntry(amountMl = amount)
                        viewModel.insert(entry)
                        editTextAmount.text.clear()
                        Toast.makeText(this, "Entry added!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Please enter a positive number", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }
    }
}