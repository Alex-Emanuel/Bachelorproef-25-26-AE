package com.example.dpdetectorapplication.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.util.Log
import android.os.Handler
import android.os.Looper
import com.example.dpdetectorapplication.services.ScreenCaptureService

class MyAccessibilityService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var lastScreenshotTime = 0L
    private val screenshotCooldown = 2000L

    private val contentChangedRunnable = Runnable {
        Log.d(
            "DPDetector",
            "UI is 500ms stabiel - ${StreamingAppState.currentStreamingService}"
        )

        // Hier komt straks de trigger voor een screenshot
        requestScreenshot()
    }

    private fun requestScreenshot() {
        if (!StreamingAppState.isStreamingAppActive) {
            return
        }

        val now = System.currentTimeMillis()
        if (now - lastScreenshotTime < screenshotCooldown) {
            Log.d("DPDetector","Screenshot overgeslagen: cooldown actief")
            return
        }
        lastScreenshotTime = now

        val intent = Intent(this,ScreenCaptureService::class.java).apply {
            action = ScreenCaptureService.ACTION_SCREENSHOT
        }
        startService(intent)

        Log.d(
            "DPDetector",
            "Screenshot aangevraagd voor ${StreamingAppState.currentStreamingService}"
        )
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: return

        val streamingService = when (packageName) {
            "com.netflix.mediaclient" -> "Netflix"
            "com.amazon.avod.thirdpartyclient" -> "Prime Video"
            else -> null
        }

        if (streamingService == null) {
            // Niet meer in ondersteunde streamingapp
            StreamingAppState.currentStreamingService = null
            StreamingAppState.isStreamingAppActive = false
            return
        }

        StreamingAppState.currentStreamingService = streamingService
        StreamingAppState.isStreamingAppActive = true

        when (event.eventType) {

            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                Log.d(
                    "DPDetector",
                    "Nieuw scherm: $streamingService"
                )

                // Bestaande timer annuleren + nieuwe timer van 500 ms starten
                handler.removeCallbacks(contentChangedRunnable)
                handler.postDelayed(contentChangedRunnable, 500)
            }

            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                Log.d(
                    "DPDetector",
                    "Content veranderd: $streamingService"
                )

                // Bestaande timer annuleren + nieuwe timer van 500 ms starten
                handler.removeCallbacks(contentChangedRunnable)
                handler.postDelayed(contentChangedRunnable, 500)
            }
        }
    }

    override fun onInterrupt() {
        // Interrupt any ongoing feedback
        Log.d("DPDetector", "Accessibility Service onderbroken")
    }

    override fun onServiceConnected() {
        // Perform initialization here
        super.onServiceConnected()

        Log.d("DPDetector", "Accessibility Service gestart")
    }
}