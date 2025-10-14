package com.example.bitfit.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Insert
    suspend fun insert(entry: WaterEntry)

    @Query("SELECT * FROM water_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<WaterEntry>>

    @Delete
    suspend fun delete(entry: WaterEntry)

    @Query("DELETE FROM water_entries")
    suspend fun deleteAll()
}