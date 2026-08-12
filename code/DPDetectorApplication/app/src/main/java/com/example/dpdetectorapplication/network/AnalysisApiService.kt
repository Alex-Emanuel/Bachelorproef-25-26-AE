package com.example.dpdetectorapplication.network

import com.example.dpdetectorapplication.ApiConfig
import com.example.dpdetectorapplication.data.model.AnalysisResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

private val retrofit = Retrofit.Builder()
    .baseUrl(ApiConfig.BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface AnalysisApiService {
    @Multipart
    @POST("analyze")
    suspend fun analyze(
        @Part file: MultipartBody.Part
    ): AnalysisResponse
}

object AnalysisApi {
    val retrofitService: AnalysisApiService by lazy {
        retrofit.create(AnalysisApiService::class.java)
    }
}