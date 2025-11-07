package com.example.bitfit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import com.example.bitfit.data.WaterEntry
import com.example.bitfit.viewmodel.WaterViewModel

class EntryListFragment : Fragment() {

    private lateinit var viewModel: WaterViewModel
    private lateinit var adapter: WaterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entry_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity())[WaterViewModel::class.java]

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        adapter = WaterAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe data changes
        viewModel.allEntries.observe(viewLifecycleOwner) { entries ->
            entries?.let { adapter.submitList(it) }
        }

        // Set up add button
        val editTextAmount = view.findViewById<EditText>(R.id.et_amount)
        val buttonAdd = view.findViewById<Button>(R.id.btn_add)

        buttonAdd.setOnClickListener {
            val amountText = editTextAmount.text.toString()

            if (amountText.isNotEmpty()) {
                try {
                    val amount = amountText.toInt()
                    if (amount > 0) {
                        val entry = WaterEntry(amountMl = amount)
                        viewModel.insert(entry)
                        editTextAmount.text.clear()
                        Toast.makeText(requireContext(), "Entry added!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Please enter a positive number", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }
    }
}