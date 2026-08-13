package com.example.dpdetectorapplication.data.model

import java.util.Date

enum class Impact(val displayName: String) {
    LAAG("Laag"),
    GEMIDDELD("Gemiddeld"),
    HOOG("Hoog")
}

data class Detectie(
    val id: Int,
    val patroonId: Int,
    val zekerheid: Int,
    val impact: Impact,
    val datumTijd: Date,
    val afbeelding: String,
    var gelezen: Boolean,
    val beschrijvingDetectie: String,
    val streamingdienst: String,
)

