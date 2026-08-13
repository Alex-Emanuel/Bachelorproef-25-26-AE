package com.example.dpdetectorapplication.data.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "detecties")
data class DetectieEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val patroonId: Int,
    val patroonNaam: String,
    val zekerheid: Int,
    val impact: String,
    val datumTijd: Long,
    val afbeelding: String,
    val gelezen: Boolean,
    val beschrijvingDetectie: String,
    val streamingdienst: String
)