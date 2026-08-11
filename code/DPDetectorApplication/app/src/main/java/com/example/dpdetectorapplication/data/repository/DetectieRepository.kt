package com.example.dpdetectorapplication.data.repository

import com.example.dpdetectorapplication.data.model.Detectie

object DetectieRepository {

    private val detections = listOf(
        Detectie(
            id = "countdown_timer",
            title = "Countdown Timer",
            certainty = "Zekerheid: 66%",
            impact = "Impact: Hoog",
            dateTime = "30/07/2026 13:45"
        ),
        Detectie(
            id = "false_hierarchy",
            title = "False Hierarchy",
            certainty = "Zekerheid: 86%",
            impact = "Impact: Laag",
            dateTime = "30/07/2026 13:42"
        ),
        Detectie(
            id = "hidden_information",
            title = "Hidden Information",
            certainty = "Zekerheid: 60%",
            impact = "Impact: Gemiddeld",
            dateTime = "23/07/2026 12:45"
        ),
        Detectie(
            id = "countdown_timer",
            title = "Countdown Timer",
            certainty = "Zekerheid: 66%",
            impact = "Impact: Hoog",
            dateTime = "30/07/2026 13:45"
        ),
        Detectie(
            id = "false_hierarchy",
            title = "False Hierarchy",
            certainty = "Zekerheid: 86%",
            impact = "Impact: Laag",
            dateTime = "30/07/2026 13:42"
        ),
        Detectie(
            id = "countdown_timer",
            title = "Countdown Timer",
            certainty = "Zekerheid: 66%",
            impact = "Impact: Hoog",
            dateTime = "30/07/2026 13:45"
        ),
        Detectie(
            id = "false_hierarchy",
            title = "False Hierarchy",
            certainty = "Zekerheid: 86%",
            impact = "Impact: Laag",
            dateTime = "30/07/2026 13:42"
        ),
        Detectie(
            id = "countdown_timer",
            title = "Countdown Timer",
            certainty = "Zekerheid: 66%",
            impact = "Impact: Hoog",
            dateTime = "30/07/2026 13:45"
        ),
        Detectie(
            id = "false_hierarchy",
            title = "False Hierarchy",
            certainty = "Zekerheid: 86%",
            impact = "Impact: Laag",
            dateTime = "30/07/2026 13:42"
        ),
        Detectie(
            id = "countdown_timer",
            title = "Countdown Timer",
            certainty = "Zekerheid: 66%",
            impact = "Impact: Hoog",
            dateTime = "30/07/2026 13:45"
        ),
        Detectie(
            id = "false_hierarchy",
            title = "False Hierarchy",
            certainty = "Zekerheid: 86%",
            impact = "Impact: Laag",
            dateTime = "30/07/2026 13:42"
        ),
    )

    fun getDetections(): List<Detectie> {
        return detections
        //return listOf()
    }

    fun getDetection(id: String): Detectie? {
        return detections.find { it.id == id }
    }
}