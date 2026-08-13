package com.example.dpdetectorapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalysisResponse(
    val filename: String,
    val result: AnalysisResult
)

@Serializable
data class AnalysisResult(
    val detected: Boolean,
    val patterns: List<DetectedPattern>,
    val confidence: Int
)

@Serializable
data class DetectedPattern(
    val id: Int,
    val name: String,
    val description: String,
    val impact: String
)