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

    fun getDetectionGroups(): List<Pair<String, List<Detectie>>> {
        val groepen = detections
            .groupBy { detectie ->
                getDatumGroep(detectie.datumTijd)
            }

        return sorteerDatumGroepen(groepen)
    }

    fun markAsRead(id: String) {
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