package com.example.dpdetectorapplication.data.model

data class DarkPattern(
    val id: Int,
    val naam: String,
    val uitleg: String,
    val gevolgen: List<String>
)

/*
Bij een verdere uitwerking van de POC zouden de dark patterns opgeslagen kunnen worden in een databank.
Voor de POC werd echter gekozen om deze gegevens statisch in de applicatie te definiëren.
*/
val darkPatterns = listOf(
    DarkPattern(
        id = 1,
        naam = "Nagging",
        uitleg = "Er verschijnt steeds een onverwacht pop-upvenster, waardoor de activiteiten van de gebruiker worden verstoord.",
        gevolgen = listOf(
            "De gebruiker kan frustratie en irritatie ervaren door herhaalde onderbrekingen.",
            "De gebruiker kan uiteindelijk de voorgestelde actie uitvoeren om verdere onderbrekingen te vermijden.",
            "De cognitieve belasting van de gebruiker kan toenemen door de herhaalde afleiding."
        )
    ),

    DarkPattern(
        id = 2,
        naam = "Roach Motel",
        uitleg = "Gemakkelijk om je aan te melden, maar onmogelijk of moeilijk om je af te melden.",
        gevolgen = listOf(
            "De gebruiker kan langer aan een dienst verbonden blijven dan gewenst.",
            "De gebruiker kan een gevoel van machteloosheid ervaren doordat het moeilijk is om een eerdere keuze ongedaan te maken.",
            "De gebruiker kan financieel nadeel ondervinden wanneer een abonnement of betalende dienst moeilijk kan worden stopgezet."
        )
    ),

    DarkPattern(
        id = 3,
        naam = "Preselection",
        uitleg = "Sommige keuzes zijn standaard vooraf geselecteerd.",
        gevolgen = listOf(
            "De gebruiker kan akkoord gaan met een optie zonder deze bewust te hebben geselecteerd.",
            "De gebruiker kan meer persoonlijke gegevens delen dan oorspronkelijk gewenst.",
            "De gebruiker kan onbedoeld een aankoop of andere actie uitvoeren die hij zonder de vooraf geselecteerde optie niet zou hebben uitgevoerd."
        )
    ),

    DarkPattern(
        id = 4,
        naam = "Hidden Information",
        uitleg = "Opties of acties zijn voor de gebruiker moeilijk te lezen of te begrijpen.",
        gevolgen = listOf(
            "De gebruiker kan een beslissing nemen zonder over alle relevante informatie te beschikken.",
            "De gebruiker kan belangrijke voorwaarden, kosten of gevolgen over het hoofd zien.",
            "De gebruiker kan meer tijd en aandacht moeten besteden aan het begrijpen van de beschikbare opties."
        )
    ),

    DarkPattern(
        id = 5,
        naam = "False Hierarchy",
        uitleg = "Eén optie wordt prominenter weergegeven dan andere, eveneens beschikbare opties.",
        gevolgen = listOf(
            "De gebruiker kan onbewust naar de meest prominente optie worden gestuurd.",
            "De gebruiker kan een keuze maken die niet overeenkomt met zijn oorspronkelijke voorkeur.",
            "De gebruiker kan minder bewust alternatieven overwegen voordat hij een beslissing neemt."
        )
    ),

    DarkPattern(
        id = 6,
        naam = "Tricked Questions",
        uitleg = "Er wordt verwarrende of te complexe bewoordingen gebruikt om iets uit te leggen of vragen te stellen.",
        gevolgen = listOf(
            "De gebruiker kan een keuze maken zonder de gevolgen ervan volledig te begrijpen.",
            "De gebruiker kan meer cognitieve belasting ervaren tijdens het beslissingsproces.",
            "De gebruiker kan onbedoeld toestemming geven of een keuze maken die hij bij duidelijke formuleringen niet zou hebben gemaakt."
        )
    ),

    DarkPattern(
        id = 7,
        naam = "Forced Enrollment",
        uitleg = "Gebruikers moeten zich registreren of aanmelden voordat ze toegang krijgen tot de dienst.",
        gevolgen = listOf(
            "De gebruiker kan persoonlijke gegevens moeten verstrekken terwijl dit niet noodzakelijk is.",
            "De gebruiker kan zich gedwongen voelen om een account aan te maken om toegang te krijgen tot de dienst.",
            "De gebruiker kan minder controle ervaren over welke persoonlijke gegevens worden verzameld."
        )
    ),

    DarkPattern(
        id = 8,
        naam = "Countdown Timer",
        uitleg = "De kans eindigt binnenkort met een duidelijke visuele aanwijzing.",
        gevolgen = listOf(
            "De gebruiker kan een gevoel van urgentie en druk ervaren.",
            "De gebruiker kan sneller een beslissing nemen zonder voldoende tijd om alternatieven te overwegen.",
            "De gebruiker kan een aankoop of andere actie uitvoeren die hij zonder de tijdsdruk niet zou hebben uitgevoerd."
        )
    ),
)