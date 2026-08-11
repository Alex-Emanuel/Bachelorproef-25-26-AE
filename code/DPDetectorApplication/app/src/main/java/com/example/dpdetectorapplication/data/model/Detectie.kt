package com.example.dpdetectorapplication.data.model

import java.util.Date

data class Detectie(
    val id: String,
    val titel: String,
    val zekerheid: String,
    val impact: String,
    val datumTijd: Date,
    val afbeeldingResId: Int,
    var gelezen: Boolean = false
)