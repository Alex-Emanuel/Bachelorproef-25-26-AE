package com.example.dpdetectorapplication.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.accessibilityservice.FingerprintGestureController
import android.accessibilityservice.AccessibilityButtonController
import android.accessibilityservice.GestureDescription
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.graphics.Path
import android.os.Build
import android.media.AudioManager
import android.content.Context
import android.util.Log
import android.os.Handler
import android.os.Looper

class MyAccessibilityService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())

    private val contentChangedRunnable = Runnable {
        Log.d("DPDetector", "UI is 500ms stabiel")
    }
    private var lastPackage: String? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: return

        if (packageName != "com.netflix.mediaclient" &&
            packageName != "com.amazon.avod.thirdpartyclient"
        ) {
            return
        }

        when (event.eventType) {

            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                Log.d(
                    "DPDetector",
                    "Nieuw scherm: $packageName"
                )

                handler.removeCallbacks(contentChangedRunnable)
                handler.postDelayed(contentChangedRunnable, 500)
            }

            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                Log.d(
                    "DPDetector",
                    "Content veranderd: $packageName"
                )

                // Bestaande timer annuleren
                handler.removeCallbacks(contentChangedRunnable)

                // Nieuwe timer van 500 ms starten
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