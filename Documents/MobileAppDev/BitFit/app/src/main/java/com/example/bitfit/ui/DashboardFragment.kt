package com.example.bitfit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bitfit.R
import com.example.bitfit.viewmodel.WaterViewModel

class DashboardFragment : Fragment() {

    private lateinit var viewModel: WaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel (shared with EntryListFragment)
        viewModel = ViewModelProvider(requireActivity())[WaterViewModel::class.java]

        val tvTotalEntries = view.findViewById<TextView>(R.id.tv_total_entries)
        val tvAverageAmount = view.findViewById<TextView>(R.id.tv_average_amount)
        val tvTotalAmount = view.findViewById<TextView>(R.id.tv_total_amount)
        val tvMinAmount = view.findViewById<TextView>(R.id.tv_min_amount)
        val tvMaxAmount = view.findViewById<TextView>(R.id.tv_max_amount)

        // Observe and calculate statistics
        viewModel.allEntries.observe(viewLifecycleOwner) { entries ->
            if (entries.isNullOrEmpty()) {
                tvTotalEntries.text = "0"
                tvAverageAmount.text = "0 ml"
                tvTotalAmount.text = "0 ml"
                tvMinAmount.text = "N/A"
                tvMaxAmount.text = "N/A"
            } else {
                val amounts = entries.map { it.amountMl }
                val total = amounts.sum()
                val average = total / entries.size
                val min = amounts.minOrNull() ?: 0
                val max = amounts.maxOrNull() ?: 0

                tvTotalEntries.text = entries.size.toString()
                tvAverageAmount.text = "$average ml"
                tvTotalAmount.text = "$total ml"
                tvMinAmount.text = "$min ml"
                tvMaxAmount.text = "$max ml"
            }
        }
    }
}