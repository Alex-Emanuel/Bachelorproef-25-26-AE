package com.example.dpdetectorapplication.data.repository

import android.annotation.SuppressLint
import com.example.dpdetectorapplication.R
import com.example.dpdetectorapplication.data.model.Detectie
import com.example.dpdetectorapplication.data.model.Impact
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
private val datumFormatter =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

object DetectieRepository {
    //private val detections = emptyList<Detectie>()
    private val detections = listOf(
        // Vandaag
        Detectie(
            id = 1,
            patroonId = 1,
            zekerheid = 66,
            impact = Impact.HOOG,
            datumTijd = datumFormatter.parse("11/08/2026 11:42")!!,
            afbeelding = "countdown-1.jpg",
            gelezen = false,
            beschrijvingDetectie = "Er is een afteltimer zichtbaar die aangeeft dat een aanbieding nog slechts beperkte tijd beschikbaar is.",
            streamingdienst = "Netflix",
        ),
    )

    fun getDetections(): List<Detectie> {
        return detections
    }

    fun getDetection(id: Int): Detectie? {
        return detections.find { it.id == id }
    }

    fun getDetectionGroups(): List<Pair<String, List<Detectie>>> {
        val groepen = detections
            .groupBy { detectie ->
                getDatumGroep(detectie.datumTijd)
            }

        return sorteerDatumGroepen(groepen)
    }

    fun markAsRead(id: Int) {
        detections.find { detectie ->
            detectie.id == id
        }?.gelezen = true
    }

    fun sorteerDatumGroepen(
        groepen: Map<String, List<Detectie>>
    ): List<Pair<String, List<Detectie>>> {
        val volgorde = mapOf(
            "Vandaag" to 0,
            "Gisteren" to 1,
            "Deze week" to 2,
            "Deze maand" to 3,
            "Vorige maand" to 4,
            "Ouder dan vorige maand" to 5
        )

        return groepen
            .toList()
            .sortedBy { (groep, _) ->
                volgorde[groep] ?: Int.MAX_VALUE
            }
    }

    fun getDatumGroep(datumTijd: Date): String {
        val vandaag = Calendar.getInstance()

        val datum = Calendar.getInstance().apply {
            time = datumTijd
        }

        // Vandaag
        if (isZelfdeDag(datum, vandaag)) {
            return "Vandaag"
        }

        // Gisteren
        val gisteren = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }

        if (isZelfdeDag(datum, gisteren)) {
            return "Gisteren"
        }

        // Deze week
        val beginVanDezeWeek = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (datum >= beginVanDezeWeek) {
            return "Deze week"
        }

        // Deze maand
        if (
            datum.get(Calendar.YEAR) == vandaag.get(Calendar.YEAR) &&
            datum.get(Calendar.MONTH) == vandaag.get(Calendar.MONTH)
        ) {
            return "Deze maand"
        }

        // Vorige maand
        val vorigeMaand = Calendar.getInstance().apply {
            add(Calendar.MONTH, -1)
        }

        if (
            datum.get(Calendar.YEAR) == vorigeMaand.get(Calendar.YEAR) &&
            datum.get(Calendar.MONTH) == vorigeMaand.get(Calendar.MONTH)
        ) {
            return "Vorige maand"
        }

        return "Ouder dan vorige maand"
    }

    private fun isZelfdeDag(datum1: Calendar, datum2: Calendar): Boolean {
        return datum1.get(Calendar.YEAR) == datum2.get(Calendar.YEAR) &&
                datum1.get(Calendar.DAY_OF_YEAR) == datum2.get(Calendar.DAY_OF_YEAR)
    }
}