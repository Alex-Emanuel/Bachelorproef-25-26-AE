package com.example.dpdetectorapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dpdetectorapplication.ui.theme.DPDetectorApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

// Voorbeeld repo:
// https://github.com/android/compose-samples/tree/main/Jetsnack/app/src/main/java/com/example/jetsnack/ui
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DPDetectorApplicationTheme {
                DPDetectorNavigation()
            }
        }
    }
}
