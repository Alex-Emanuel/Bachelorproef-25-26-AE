package com.example.dpdetectorapplication.services

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.content.getSystemService
import androidx.core.graphics.createBitmap
import java.io.File
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import android.content.pm.ServiceInfo
import com.example.dpdetectorapplication.R
import com.example.dpdetectorapplication.accessibility.StreamingAppState
import com.example.dpdetectorapplication.analysis.AnalysisManager
import com.example.dpdetectorapplication.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ScreenCaptureService : Service() {

    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private val mediaProjectionManager by lazy {
        getSystemService<MediaProjectionManager>()
    }
    @Inject
    lateinit var analysisManager: AnalysisManager

    private val mediaProjectionCallback = object : MediaProjection.Callback() {
        override fun onStop() {
            super.onStop()

            isCaptureActive = false

            virtualDisplay?.release()
            virtualDisplay = null

            imageReader?.close()
            imageReader = null

            mediaProjection = null

            sendBroadcast(Intent(ACTION_CAPTURE_STOPPED).apply{ setPackage(packageName) })

            Log.d("ScreenCapture", "MediaProjection gestopt")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent == null) {
            Log.e("ScreenCapture", "Geen intent ontvangen")
            return START_NOT_STICKY
        }

        when (intent.action) {

            ACTION_START -> {
                Log.d("ScreenCapture", "Capture starten")
                startMediaProjection(intent)
            }

            ACTION_SCREENSHOT -> {
                Log.d("ScreenCapture", "Screenshot gevraagd")
                takeScreenshot()
            }

            ACTION_STOP -> {
                Log.d("ScreenCapture", "Capture stoppen")
                stopCapture()
            }
        }

        return START_NOT_STICKY
    }

    private fun startMediaProjection(intent: Intent) {

        val resultCode = intent.getIntExtra(EXTRA_RESULT_CODE,-1)

        Log.d("ScreenCapture","resultCode = $resultCode")
        Log.d("ScreenCapture","extras = ${intent.extras}")

        val resultData =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_RESULT_DATA,Intent::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_RESULT_DATA)
            }

        if (resultCode != Activity.RESULT_OK || resultData == null) {
            Log.e("ScreenCapture","Ongeldige MediaProjection data")
            return        }

        startForegroundService()

        Log.d("ScreenCapture","Foreground service gestart")

        // mediaProjection = mediaProjectionManager?.getMediaProjection(resultCode, resultData)

        mediaProjection = try {
            mediaProjectionManager?.getMediaProjection(resultCode, resultData)
        } catch (e: Exception) {
            Log.e("ScreenCapture", "MediaProjection kon niet worden aangemaakt",e)
            sendBroadcast(Intent(ACTION_CAPTURE_STOPPED).apply{ setPackage(packageName) })
            stopSelf()
            return
        }

        if (mediaProjection == null) {
            Log.e("ScreenCapture", "MediaProjection kon niet worden aangemaakt")
            sendBroadcast(Intent(ACTION_CAPTURE_STOPPED).apply{ setPackage(packageName) })
            stopSelf()
            return
        }

        mediaProjection?.registerCallback(mediaProjectionCallback,null)

        startCaptureService()
    }

    private fun startCaptureService() {
        try {
            val (width, height) = getScreenSize()
            createImageReader(width, height)
            createVirtualDisplay(width, height)

            if (virtualDisplay == null) {
                throw IllegalStateException("VirtualDisplay kon niet worden aangemaakt")
            }

            isCaptureActive = true

            Log.d("ScreenCapture", "MediaProjection sessie actief")
            sendBroadcast(Intent(ACTION_CAPTURE_STARTED).apply{ setPackage(packageName) })

        } catch (e: Exception) {
            Log.e("ScreenCapture", "Capture starten mislukt", e)
            stopCapture()
        }
    }

    /*private fun startCaptureService() {
        val (width, height) = getScreenSize()

        createImageReader(width, height)
        createVirtualDisplay(width, height)

        Log.d("ScreenCapture", "MediaProjection sessie actief")
    }*/

    private fun startForegroundService() {
        val channelId = "screen_capture"
        val notificationManager = getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Screen capture",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("DPDetector")
            .setContentText("Screen capture actief")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "DPDetector is momenteel actief en maakt screenshots voor analyse."
                    )
            )
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                1,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
            )
        } else {
            startForeground(1, notification)
        }
    }

    private fun getScreenSize(): Pair<Int, Int> {
        val metrics = resources.displayMetrics
        return metrics.widthPixels to metrics.heightPixels
    }

    private fun createImageReader(width: Int, height: Int) {
        imageReader = ImageReader.newInstance(
            width,
            height,
            PixelFormat.RGBA_8888,
            2
        )
    }

    private fun createVirtualDisplay(width: Int, height: Int) {
        virtualDisplay = mediaProjection?.createVirtualDisplay(
            "ScreenCapture",
            width,
            height,
            resources.displayMetrics.densityDpi,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader?.surface,
            null,
            null
        )
    }

    private fun takeScreenshot() {
        val streamingService = StreamingAppState.currentStreamingService
        val image = imageReader?.acquireLatestImage()

        if (image == null) {
            Log.d("ScreenCapture", "Nog geen image beschikbaar")
            return
        }

        val bitmap = imageToBitmap(image)
        image.close()

        Log.d(
            "ScreenCapture",
            "Screenshot genomen: ${bitmap.width}x${bitmap.height}"
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = saveScreenshot(bitmap)

                // Analyse via FastAPI + indien dp opgeslagen in db en detections map
                var dienst = "Eigen screenshot"
                if(streamingService != null)
                    dienst = streamingService

                val response = analysisManager.analyse(file, dienst)
                Log.d("ScreenCapture","Analyse resultaat: ${response.result}")

                // Verwijderen na analyse uit cache
                file.delete()

            } catch (e: Exception) {
                Log.e("ScreenCapture","Analyse mislukt",e)
            }
        }
    }

    private fun imageToBitmap(image: Image): Bitmap {
        val plane = image.planes[0]

        val buffer = plane.buffer
        val pixelStride = plane.pixelStride
        val rowStride = plane.rowStride
        val rowPadding = rowStride - pixelStride * image.width

        val bitmap = createBitmap(image.width + rowPadding / pixelStride, image.height)

        bitmap.copyPixelsFromBuffer(buffer)

        return Bitmap.createBitmap(bitmap,0,0,image.width,image.height)
    }

    private fun saveScreenshot(bitmap: Bitmap): File {
        val file = File(
            cacheDir,
            "screenshot_${System.currentTimeMillis()}.png"
        )

        file.outputStream().use { outputStream ->
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                outputStream
            )
        }

        Log.d(
            "ScreenCapture",
            "Screenshot opgeslagen: ${file.absolutePath}"
        )

        return file
    }

    private fun stopCapture() {
        Log.d("ScreenCapture", "MediaProjection stoppen")

        mediaProjection?.stop()

        stopSelf()
    }

    override fun onDestroy() {
        isCaptureActive = false

        virtualDisplay?.release()
        virtualDisplay = null

        imageReader?.close()
        imageReader = null

        mediaProjection?.unregisterCallback(mediaProjectionCallback)
        mediaProjection = null

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        @Volatile
        var isCaptureActive = false

        const val EXTRA_RESULT_CODE = "extra_result_code"
        const val EXTRA_RESULT_DATA = "extra_result_data"

        const val ACTION_START = "ACTION_START"
        const val ACTION_SCREENSHOT = "ACTION_SCREENSHOT"
        const val ACTION_STOP = "ACTION_STOP"

        const val ACTION_CAPTURE_STARTED = "com.example.dpdetectorapplication.CAPTURE_STARTED"
        const val ACTION_CAPTURE_STOPPED = "com.example.dpdetectorapplication.CAPTURE_STOPPED"
    }
}