package com.example.dpdetectorapplication.data.repository

import com.example.dpdetectorapplication.data.database.DetectieDao
import com.example.dpdetectorapplication.data.database.DetectieEntity
import com.example.dpdetectorapplication.data.model.Detectie
import com.example.dpdetectorapplication.data.model.Impact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetectieRepositoryImpl(private val dao: DetectieDao) : DetectieRepository {

    override fun getDetecties(): Flow<List<Detectie>> {
        return dao.getDetecties().map { detecties ->
            detecties.map { detectie -> detectie.toDetectie()}
        }
    }

    override suspend fun getDetectieById(id: Int): Detectie? {
        return dao.getDetectieById(id)?.toDetectie()
    }

    override suspend fun addDetectie(detectie: Detectie) {
        dao.insertDetectie(detectie.toEntity())
    }

    override suspend fun markAsRead(id: Int) {
        dao.markAsRead(id)
    }


    //Voor omzetting datum en enum impact
    private fun DetectieEntity.toDetectie(): Detectie {
        return Detectie(
            id = id,
            patroonId = patroonId,
            patroonNaam = patroonNaam,
            zekerheid = zekerheid,
            impact = Impact.valueOf(impact),
            datumTijd = java.util.Date(datumTijd),
            afbeelding = afbeelding,
            gelezen = gelezen,
            beschrijvingDetectie = beschrijvingDetectie,
            streamingdienst = streamingdienst
        )
    }

    private fun Detectie.toEntity(): DetectieEntity {
        return DetectieEntity(
            id = id,
            patroonId = patroonId,
            patroonNaam = patroonNaam,
            zekerheid = zekerheid,
            impact = impact.name,
            datumTijd = datumTijd.time,
            afbeelding = afbeelding,
            gelezen = gelezen,
            beschrijvingDetectie = beschrijvingDetectie,
            streamingdienst = streamingdienst
        )
    }
}