package com.example.dpdetectorapplication.data.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectieDao {

    @Query("SELECT * FROM detecties ORDER BY datumTijd DESC")
    fun getDetecties(): Flow<List<DetectieEntity>>

    @Query("SELECT * FROM detecties WHERE id = :id")
    suspend fun getDetectieById(id: Int): DetectieEntity?

    @Insert
    suspend fun insertDetectie(detectie: DetectieEntity)

    @Delete
    suspend fun deleteDetectie(detectie: DetectieEntity)

    @Query("UPDATE detecties SET gelezen = 1 WHERE id = :id")
    suspend fun markAsRead(id: Int)
}