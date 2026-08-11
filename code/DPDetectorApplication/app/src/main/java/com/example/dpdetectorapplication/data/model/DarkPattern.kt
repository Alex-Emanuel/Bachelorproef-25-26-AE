package com.example.dpdetectorapplication.data.model

data class DarkPattern(
    val id: String,
    val naam: String,
    val uitleg: String,
    val gevolgen: List<String>
)

val darkPatterns = listOf(
    DarkPattern(
        id = "countdown_timer",
        naam = "Countdown Timer",
        uitleg = "Een afteltimer creëert een gevoel van urgentie door te suggereren dat een aanbieding of actie slechts tijdelijk beschikbaar is.",
        gevolgen = listOf(
            "De gebruiker kan sneller beslissingen nemen zonder voldoende tijd om alternatieven te overwegen.",
            "De gebruiker kan het gevoel krijgen een aanbieding mis te lopen.",
            "De gebruiker kan een aankoop doen die hij zonder de tijdsdruk niet zou hebben gedaan."
        )
    ),

    DarkPattern(
        id = "false_hierarchy",
        naam = "False Hierarchy",
        uitleg = "Belangrijke keuzes worden visueel minder opvallend gemaakt dan keuzes die de aanbieder liever heeft dat de gebruiker selecteert.",
        gevolgen = listOf(
            "De gebruiker kan onbewust naar een bepaalde keuze worden gestuurd.",
            "Alternatieve keuzes kunnen minder snel worden opgemerkt.",
            "De gebruiker kan een keuze maken die niet overeenkomt met zijn oorspronkelijke voorkeur."
        )
    ),

    DarkPattern(
        id = "preselection",
        naam = "Preselection",
        uitleg = "Een bepaalde optie is vooraf geselecteerd, waardoor de gebruiker deze keuze kan behouden zonder hier bewust voor te kiezen.",
        gevolgen = listOf(
            "De gebruiker kan akkoord gaan met opties die hij niet bewust heeft geselecteerd.",
            "De gebruiker kan onbedoeld extra producten of diensten selecteren.",
            "De gebruiker kan instellingen behouden zonder zich bewust te zijn van de gevolgen."
        )
    ),

    DarkPattern(
        id = "hidden_information",
        naam = "Hidden Information",
        uitleg = "Belangrijke informatie wordt verborgen, minder opvallend weergegeven of pas op een later moment zichtbaar gemaakt.",
        gevolgen = listOf(
            "De gebruiker kan een beslissing nemen zonder over alle relevante informatie te beschikken.",
            "Belangrijke voorwaarden kunnen over het hoofd worden gezien.",
            "De gebruiker kan achteraf geconfronteerd worden met onverwachte voorwaarden of kosten."
        )
    ),

    DarkPattern(
        id = "roach_motel",
        naam = "Roach Motel",
        uitleg = "Een actie is gemakkelijk uit te voeren, maar moeilijk om terug te draaien of ongedaan te maken.",
        gevolgen = listOf(
            "De gebruiker kan gemakkelijk een abonnement starten, maar moeite hebben om dit weer op te zeggen.",
            "De gebruiker kan langer aan een dienst verbonden blijven dan gewenst.",
            "De gebruiker kan opgeven voordat hij erin slaagt een actie ongedaan te maken."
        )
    ),

    DarkPattern(
        id = "forced_enrollment",
        naam = "Forced Enrollment",
        uitleg = "De gebruiker wordt verplicht om zich te registreren of een account aan te maken voordat hij toegang krijgt tot bepaalde functionaliteit.",
        gevolgen = listOf(
            "De gebruiker kan persoonlijke gegevens moeten verstrekken terwijl dit niet noodzakelijk is.",
            "De gebruiker kan een account aanmaken terwijl hij dit eigenlijk niet wil.",
            "De gebruiker kan minder controle hebben over welke persoonlijke gegevens worden verzameld."
        )
    ),

    DarkPattern(
        id = "nagging",
        naam = "Nagging",
        uitleg = "De gebruiker wordt herhaaldelijk onderbroken of aangespoord om een bepaalde actie uit te voeren.",
        gevolgen = listOf(
            "De gebruiker kan geïrriteerd raken door herhaalde meldingen of verzoeken.",
            "De gebruiker kan uiteindelijk een actie uitvoeren om de onderbrekingen te stoppen.",
            "De normale gebruikerservaring kan worden verstoord."
        )
    )
)