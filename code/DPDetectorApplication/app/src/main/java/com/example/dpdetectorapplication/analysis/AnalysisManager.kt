package com.example.dpdetectorapplication.analysis

import android.util.Log
import com.example.dpdetectorapplication.data.model.AnalysisResponse
import com.example.dpdetectorapplication.network.AnalysisApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AnalysisManager {

    suspend fun analyse(imageFile: File): AnalysisResponse {

        Log.d("AnalysisManager","Analyse gestart voor: ${imageFile.absolutePath}")

        val requestFile = imageFile.asRequestBody("image/png".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("file",imageFile.name,requestFile)

        val response = AnalysisApi.retrofitService.analyze(multipartBody)

        return response
    }
}