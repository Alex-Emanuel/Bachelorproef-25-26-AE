package com.example.dpdetectorapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalysisResponse(
    val filename: String,
    val result: String
)