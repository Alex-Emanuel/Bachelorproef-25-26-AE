package com.example.dpdetectorapplication.ui.home

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dpdetectorapplication.services.ScreenCaptureService
import android.accessibilityservice.AccessibilityServiceInfo
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.dpdetectorapplication.accessibility.MyAccessibilityService

enum class HomeTab {
    Detecties,
    Instellingen
}

@Composable
fun HomeScreen(
    onItemClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isActive by rememberSaveable {
        mutableStateOf(ScreenCaptureService.isCaptureActive)
    }
    var waitingForAccessibility by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    // Screencapture actief?
    LaunchedEffect(Unit) {
        isActive = ScreenCaptureService.isCaptureActive
    }

    val mediaProjectionManager = context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    val screenCaptureLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data
            if (resultData != null) {
                Log.d("DPDetector","MediaProjection toestemming gegeven")

                val serviceIntent = Intent(context,ScreenCaptureService::class.java).apply {
                    action = ScreenCaptureService.ACTION_START
                    putExtra(
                        ScreenCaptureService.EXTRA_RESULT_CODE,
                        result.resultCode
                    )
                    putExtra(
                        ScreenCaptureService.EXTRA_RESULT_DATA,
                        resultData
                    )
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            }
        } else {
            isActive = false
            Log.d("DPDetector","MediaProjection toestemming geweigerd")
        }
    }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        granted -> Log.d("DPDetector", "Notification permission: $granted")

        // Ongeacht ja of nee, erna MediaProjection vragen
        val captureIntent = mediaProjectionManager.createScreenCaptureIntent()
        screenCaptureLauncher.launch(captureIntent)
    }

    // Accessibility service ingeschakeld?
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && waitingForAccessibility) {
                if (isAccessibilityServiceEnabled(context)) {
                    Log.d(
                        "DPDetector",
                        "Accessibility Service is ingeschakeld"
                    )
                    waitingForAccessibility = false

                    // Nu pas notificatie/MediaProjection verderzetten
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isNotificationAllowed(context)) {
                        // Notificatie-permissie
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        // Notificatie-permissie is al gegeven
                        val captureIntent = mediaProjectionManager.createScreenCaptureIntent()
                        screenCaptureLauncher.launch(captureIntent)
                    }
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Opmerken of mediaProjection draait
    val captureReceiver = remember { object : BroadcastReceiver() {
        override fun onReceive(context: Context?,intent: Intent?) {
            when (intent?.action) {
                ScreenCaptureService.ACTION_CAPTURE_STARTED -> {
                    isActive = true
                }
                ScreenCaptureService.ACTION_CAPTURE_STOPPED -> {
                    isActive = false
                }
            }
        }
    }}
    DisposableEffect(context) {
        val filter = IntentFilter().apply {
            addAction(ScreenCaptureService.ACTION_CAPTURE_STARTED)
            addAction(ScreenCaptureService.ACTION_CAPTURE_STOPPED)
        }
        ContextCompat.registerReceiver(context,captureReceiver,filter,ContextCompat.RECEIVER_NOT_EXPORTED)
        onDispose { context.unregisterReceiver(captureReceiver) }
    }

    // Interface
    var selectedTab by rememberSaveable {
        mutableStateOf(HomeTab.Detecties)
    }

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp,
                    bottom = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DP Detector",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = if (isActive) "Actief" else "Inactief",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = isActive,
                onCheckedChange = { actief ->
                    if (actief) {
                        if (!isAccessibilityServiceEnabled(context)) {
                            // Accessibility nog niet ingeschakeld
                            waitingForAccessibility = true
                            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            context.startActivity(intent)
                        } else {
                            // Accessibility ingeschakeld
                            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isNotificationAllowed(context)) {
                                // Notificatie-permissie
                                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                // Notificatie-permissie is al gegeven
                                val captureIntent = mediaProjectionManager.createScreenCaptureIntent()
                                screenCaptureLauncher.launch(captureIntent)
                            }
                            Log.d("DPDetector", "Detector is geactiveerd")
                        }
                    }
                    else {
                        val stopIntent = Intent(context,ScreenCaptureService::class.java).apply {
                            action = ScreenCaptureService.ACTION_STOP
                        }
                        context.startService(stopIntent)

                        isActive = false

                        Log.d("DPDetector", "Detector is gedeactiveerd")
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colorScheme.tertiary,

                    uncheckedThumbColor = colorScheme.onBackground,
                    uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f)
                )
            )
        }

        PrimaryTabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = colorScheme.background,
            contentColor = colorScheme.onBackground,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectedTabIndex = selectedTab.ordinal,
                        matchContentSize = false
                    ),
                    width = Dp.Unspecified,
                    height = 2.dp,
                    color = colorScheme.onBackground
                )
            }
        ) {
            Tab(
                selected = selectedTab == HomeTab.Detecties,
                onClick = {
                    selectedTab = HomeTab.Detecties
                },
                text = {
                    Text(
                        text = "Detecties",
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            Tab(
                selected = selectedTab == HomeTab.Instellingen,
                onClick = {
                    selectedTab = HomeTab.Instellingen
                },
                text = {
                    Text(
                        text = "Instellingen",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }

        when (selectedTab) {
            HomeTab.Detecties -> {
                /*Button(
                    onClick = {
                        Log.d(
                            "DPDetector",
                            "Screenshot-knop ingedrukt"
                        )

                        val screenshotIntent = Intent(context,ScreenCaptureService::class.java).apply {
                            action = ScreenCaptureService.ACTION_SCREENSHOT
                        }

                        context.startService(screenshotIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text("Maak screenshot")
                }*/

                DetectiesScreen(
                    onItemClick = onItemClick,
                    onDisclaimerClick = {
                        // TODO: disclaimer openen
                    },
                    onOpenWidgetClick = {
                        // TODO: widget openen
                    }
                )

            }

            HomeTab.Instellingen -> {
                InstellingenScreen()
            }
        }
    }
}

fun isNotificationAllowed(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        NotificationManagerCompat
            .from(context)
            .areNotificationsEnabled()
    }
}

fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
    )

    return enabledServices.any { service ->
        service.resolveInfo.serviceInfo.packageName == context.packageName &&
                service.resolveInfo.serviceInfo.name == MyAccessibilityService::class.java.name
    }
}