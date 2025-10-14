package com.example.bitfit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import com.example.bitfit.data.WaterEntry

class WaterAdapter : ListAdapter<WaterEntry, WaterAdapter.WaterViewHolder>(WaterComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_water_entry, parent, false)
        return WaterViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaterViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class WaterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView: TextView = itemView.findViewById(R.id.tv_amount)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(entry: WaterEntry) {
            amountTextView.text = "${entry.amountMl} ml"
            dateTextView.text = entry.getFormattedDate()
        }
    }

    class WaterComparator : DiffUtil.ItemCallback<WaterEntry>() {
        override fun areItemsTheSame(oldItem: WaterEntry, newItem: WaterEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WaterEntry, newItem: WaterEntry): Boolean {
            return oldItem == newItem
        }
    }
}