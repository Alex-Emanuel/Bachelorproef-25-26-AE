package com.example.dpdetectorapplication.accessibility

object StreamingAppState {

    @Volatile
    var currentStreamingService: String? = null

    @Volatile
    var isStreamingAppActive: Boolean = false
}