package com.example.bitfit.data

import kotlinx.coroutines.flow.Flow

class WaterRepository(private val waterDao: WaterDao) {

    val allEntries: Flow<List<WaterEntry>> = waterDao.getAllEntries()

    suspend fun insert(entry: WaterEntry) {
        waterDao.insert(entry)
    }

    suspend fun delete(entry: WaterEntry) {
        waterDao.delete(entry)
    }
}