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
    private val detections = listOf(

        // Vandaag
        Detectie(
            id = "countdown_timer_1",
            patroonId = "countdown_timer",
            zekerheid = 66,
            impact = Impact.HOOG,
            datumTijd = datumFormatter.parse("11/08/2026 11:42")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "Er is een afteltimer zichtbaar die aangeeft dat een aanbieding nog slechts beperkte tijd beschikbaar is.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "false_hierarchy_1",
            patroonId = "false_hierarchy",
            zekerheid = 86,
            impact = Impact.LAAG,
            datumTijd = datumFormatter.parse("11/08/2026 10:18")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true,
            beschrijvingDetectie = "De primaire actie wordt visueel sterker benadrukt dan de alternatieve keuze, waardoor de gebruiker richting één optie wordt gestuurd.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "preselection_1",
            patroonId = "preselection",
            zekerheid = 78,
            impact = Impact.GEMIDDELD,
            datumTijd = datumFormatter.parse("11/08/2026 09:05")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "Een optie op het scherm staat vooraf aangevinkt, waardoor de gebruiker deze keuze mogelijk behoudt zonder ze bewust te selecteren.",
            streamingdienst = "Netflix",
        ),

        // Gisteren
        Detectie(
            id = "hidden_information_1",
            patroonId = "hidden_information",
            zekerheid = 71,
            impact = Impact.GEMIDDELD,
            datumTijd = datumFormatter.parse("10/08/2026 16:34")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true,
            beschrijvingDetectie = "Belangrijke informatie over de gekozen optie is niet direct zichtbaar en bevindt zich achter een extra interactie.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "roach_motel_1",
            patroonId = "roach_motel",
            zekerheid = 91,
            impact = Impact.HOOG,
            datumTijd = datumFormatter.parse("10/08/2026 14:21")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "Het starten van de dienst is eenvoudig zichtbaar, terwijl de optie om de actie terug te draaien moeilijker te vinden is.",
            streamingdienst = "Netflix",
        ),

        // Vorige week
        Detectie(
            id = "forced_enrollment_1",
            patroonId = "forced_enrollment",
            zekerheid = 83,
            impact = Impact.HOOG,
            datumTijd = datumFormatter.parse("07/08/2026 15:47")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true,
            beschrijvingDetectie = "De gebruiker moet een account aanmaken voordat toegang tot de gewenste inhoud of functionaliteit wordt verleend.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "nagging_1",
            patroonId = "nagging",
            zekerheid = 74,
            impact = Impact.GEMIDDELD,
            datumTijd = datumFormatter.parse("05/08/2026 12:16")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "De gebruiker wordt herhaaldelijk aangespoord om een bepaalde actie uit te voeren via een opvallende melding.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "hidden_information_2",
            patroonId = "hidden_information",
            zekerheid = 62,
            impact = Impact.LAAG,
            datumTijd = datumFormatter.parse("04/08/2026 18:03")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true,
            beschrijvingDetectie = "Informatie over voorwaarden of instellingen is minder opvallend weergegeven en vereist extra interactie om te bekijken.",
            streamingdienst = "Netflix",
        ),

        // Vorige maand
        Detectie(
            id = "countdown_timer_2",
            patroonId = "countdown_timer",
            zekerheid = 88,
            impact = Impact.HOOG,
            datumTijd = datumFormatter.parse("28/07/2026 13:45")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "Een timer telt af naar het einde van een promotie en creëert hierdoor een gevoel van urgentie.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "false_hierarchy_2",
            patroonId = "false_hierarchy",
            zekerheid = 79,
            impact = Impact.LAAG,
            datumTijd = datumFormatter.parse("21/07/2026 11:32")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true,
            beschrijvingDetectie = "De gewenste actie is opvallender vormgegeven dan de alternatieve actie, waardoor de visuele hiërarchie de gebruiker beïnvloedt.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "preselection_2",
            patroonId = "preselection",
            zekerheid = 68,
            impact = Impact.GEMIDDELD,
            datumTijd = datumFormatter.parse("15/07/2026 16:09")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "Een extra optie is vooraf geselecteerd en wordt meegenomen wanneer de gebruiker verdergaat zonder de selectie aan te passen.",
            streamingdienst = "Netflix",
        ),

        // Ouder dan vorige maand
        Detectie(
            id = "roach_motel_2",
            patroonId = "roach_motel",
            zekerheid = 93,
            impact = Impact.HOOG,
            datumTijd = datumFormatter.parse("18/06/2026 14:27")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = true,
            beschrijvingDetectie = "De gebruiker kan de gewenste actie eenvoudig uitvoeren, maar het terugdraaien of annuleren ervan is moeilijker toegankelijk.",
            streamingdienst = "Netflix",
        ),
        Detectie(
            id = "nagging_2",
            patroonId = "nagging",
            zekerheid = 57,
            impact = Impact.LAAG,
            datumTijd = datumFormatter.parse("03/06/2026 09:51")!!,
            afbeeldingResId = R.drawable.test,
            gelezen = false,
            beschrijvingDetectie = "Een terugkerende melding vraagt de gebruiker opnieuw om een actie uit te voeren die eerder kon worden genegeerd.",
            streamingdienst = "Netflix",
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