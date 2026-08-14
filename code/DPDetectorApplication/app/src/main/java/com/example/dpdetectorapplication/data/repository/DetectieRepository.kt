package com.example.dpdetectorapplication.data.repository

import com.example.dpdetectorapplication.data.model.Detectie
import kotlinx.coroutines.flow.Flow

interface DetectieRepository {

    fun getDetecties(): Flow<List<Detectie>>

    suspend fun getDetectieById(id: Int): Detectie?

    suspend fun addDetectie(detectie: Detectie)

    suspend fun markAsRead(id: Int)
}