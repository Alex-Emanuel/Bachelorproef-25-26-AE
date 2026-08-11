package com.example.dpdetectorapplication.data.repository

import android.annotation.SuppressLint
import com.example.dpdetectorapplication.R
import com.example.dpdetectorapplication.data.model.Detectie
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
private val datumFormatter =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

object DetectieRepository {

    private val detections = listOf(

        // Vandaag
        Detectie(
            id = "countdown_timer_1",
            titel = "Countdown Timer",
            zekerheid = "Zekerheid: 66%",
            impact = "Impact: Hoog",
            datumTijd = datumFormatter.parse("11/08/2026 11:42")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        ),
        Detectie(
            id = "false_hierarchy_1",
            titel = "False Hierarchy",
            zekerheid = "Zekerheid: 86%",
            impact = "Impact: Laag",
            datumTijd = datumFormatter.parse("11/08/2026 10:18")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true
        ),
        Detectie(
            id = "preselection_1",
            titel = "Preselection",
            zekerheid = "Zekerheid: 78%",
            impact = "Impact: Gemiddeld",
            datumTijd = datumFormatter.parse("11/08/2026 09:05")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        ),

        // Gisteren
        Detectie(
            id = "hidden_information_1",
            titel = "Hidden Information",
            zekerheid = "Zekerheid: 71%",
            impact = "Impact: Gemiddeld",
            datumTijd = datumFormatter.parse("10/08/2026 16:34")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true
        ),
        Detectie(
            id = "roach_motel_1",
            titel = "Roach Motel",
            zekerheid = "Zekerheid: 91%",
            impact = "Impact: Hoog",
            datumTijd = datumFormatter.parse("10/08/2026 14:21")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        ),

        // Vorige week
        Detectie(
            id = "forced_enrollment_1",
            titel = "Forced Enrollment",
            zekerheid = "Zekerheid: 83%",
            impact = "Impact: Hoog",
            datumTijd = datumFormatter.parse("07/08/2026 15:47")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true
        ),
        Detectie(
            id = "nagging_1",
            titel = "Nagging",
            zekerheid = "Zekerheid: 74%",
            impact = "Impact: Gemiddeld",
            datumTijd = datumFormatter.parse("05/08/2026 12:16")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        ),
        Detectie(
            id = "hidden_information_2",
            titel = "Hidden Information",
            zekerheid = "Zekerheid: 62%",
            impact = "Impact: Laag",
            datumTijd = datumFormatter.parse("04/08/2026 18:03")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true
        ),

        // Vorige maand
        Detectie(
            id = "countdown_timer_2",
            titel = "Countdown Timer",
            zekerheid = "Zekerheid: 88%",
            impact = "Impact: Hoog",
            datumTijd = datumFormatter.parse("28/07/2026 13:45")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        ),
        Detectie(
            id = "false_hierarchy_2",
            titel = "False Hierarchy",
            zekerheid = "Zekerheid: 79%",
            impact = "Impact: Laag",
            datumTijd = datumFormatter.parse("21/07/2026 11:32")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true
        ),
        Detectie(
            id = "preselection_2",
            titel = "Preselection",
            zekerheid = "Zekerheid: 68%",
            impact = "Impact: Gemiddeld",
            datumTijd = datumFormatter.parse("15/07/2026 16:09")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        ),

        // Ouder dan vorige maand
        Detectie(
            id = "roach_motel_2",
            titel = "Roach Motel",
            zekerheid = "Zekerheid: 93%",
            impact = "Impact: Hoog",
            datumTijd = datumFormatter.parse("18/06/2026 14:27")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true
        ),
        Detectie(
            id = "nagging_2",
            titel = "Nagging",
            zekerheid = "Zekerheid: 57%",
            impact = "Impact: Laag",
            datumTijd = datumFormatter.parse("03/06/2026 09:51")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false
        )
    )

    fun getDetections(): List<Detectie> {
        return detections
    }

    fun getDetection(id: String): Detectie? {
        return detections.find { it.id == id }
    }

    fun markAsRead(id: String) {
        detections.find { it.id == id }?.gelezen = true
    }

    fun getDatumGroep(datumTijd: Date): String {
        val vandaag = Calendar.getInstance()

        val datumCalendar = Calendar.getInstance().apply {
            time = datumTijd
        }

        // Vandaag
        if (isZelfdeDag(datumCalendar, vandaag)) {
            return "Vandaag"
        }

        // Gisteren
        val gisteren = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }

        if (isZelfdeDag(datumCalendar, gisteren)) {
            return "Gisteren"
        }

        // Vorige week
        val zevenDagenGeleden = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }

        if (datumCalendar.after(zevenDagenGeleden)) {
            return "Vorige week"
        }

        // Vorige maand
        val vorigeMaand = Calendar.getInstance().apply {
            add(Calendar.MONTH, -1)
        }

        if (
            datumCalendar.get(Calendar.MONTH) == vorigeMaand.get(Calendar.MONTH) &&
            datumCalendar.get(Calendar.YEAR) == vorigeMaand.get(Calendar.YEAR)
        ) {
            return "Vorige maand"
        }

        return "Ouder dan vorige maand"
    }

    private fun isZelfdeDag(
        datum1: Calendar,
        datum2: Calendar
    ): Boolean {
        return datum1.get(Calendar.YEAR) == datum2.get(Calendar.YEAR) &&
                datum1.get(Calendar.DAY_OF_YEAR) == datum2.get(Calendar.DAY_OF_YEAR)
    }
}