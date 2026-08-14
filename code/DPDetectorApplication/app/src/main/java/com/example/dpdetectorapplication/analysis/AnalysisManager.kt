package com.example.dpdetectorapplication.analysis

import android.content.Context
import android.util.Log
import com.example.dpdetectorapplication.data.model.AnalysisResponse
import com.example.dpdetectorapplication.data.model.Detectie
import com.example.dpdetectorapplication.data.model.Impact
import com.example.dpdetectorapplication.data.repository.DetectieRepository
import com.example.dpdetectorapplication.network.AnalysisApi
import com.example.dpdetectorapplication.services.NotificationHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalysisManager @Inject constructor (
    private val repository: DetectieRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun analyse(imageFile: File, streamingdienst: String): AnalysisResponse {

        Log.d("AnalysisManager","Analyse gestart voor: ${imageFile.absolutePath}")

        val requestFile = imageFile.asRequestBody("image/png".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("file",imageFile.name,requestFile)

        val response = AnalysisApi.retrofitService.analyze(multipartBody)

        if (response.result.detected) {

            val detectionDirectory = File(context.filesDir,"detections")
            if (!detectionDirectory.exists()) {
                detectionDirectory.mkdirs()
            }

            val detectionFile = File(detectionDirectory,"detection_${System.currentTimeMillis()}.png"
            )

            imageFile.copyTo(detectionFile,overwrite = false)

            Log.d("AnalysisManager", "Detectie-afbeelding opgeslagen: ${detectionFile.absolutePath}")

            val confidence = response.result.confidence

            response.result.patterns.forEach { pattern ->

                val detectie = Detectie(
                    id = 0,
                    patroonId = pattern.id,
                    patroonNaam = pattern.name,
                    zekerheid = confidence,
                    impact = when (pattern.impact) {
                        "HIGH" -> Impact.HOOG
                        "MEDIUM" -> Impact.GEMIDDELD
                        "LOW" -> Impact.LAAG
                        else -> Impact.GEMIDDELD
                    },
                    datumTijd = Date(),
                    afbeelding = detectionFile.name,
                    gelezen = false,
                    beschrijvingDetectie = pattern.description,
                    streamingdienst = streamingdienst
                )

                repository.addDetectie(detectie)
            }

            NotificationHelper.showDarkPatternNotification(context,response)
        }

        return response
    }
}