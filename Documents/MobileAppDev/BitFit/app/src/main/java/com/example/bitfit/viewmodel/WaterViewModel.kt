package com.example.bitfit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bitfit.data.WaterDatabase
import com.example.bitfit.data.WaterEntry
import com.example.bitfit.data.WaterRepository
import kotlinx.coroutines.launch

class WaterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WaterRepository
    val allEntries: LiveData<List<WaterEntry>>

    init {
        val waterDao = WaterDatabase.getDatabase(application).waterDao()
        repository = WaterRepository(waterDao)
        allEntries = repository.allEntries.asLiveData()
    }

    fun insert(entry: WaterEntry) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun delete(entry: WaterEntry) = viewModelScope.launch {
        repository.delete(entry)
    }
}