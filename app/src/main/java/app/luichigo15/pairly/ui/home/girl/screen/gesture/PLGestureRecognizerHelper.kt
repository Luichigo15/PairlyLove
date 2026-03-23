package app.luichigo15.pairly.ui.home.girl.screen.gesture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.SystemClock
import androidx.camera.core.ImageProxy
import androidx.core.graphics.createBitmap
import app.luichigo15.pairly.R
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.gesturerecognizer.GestureRecognizer

private const val DEFAULT_HAND_DETECTION_CONFIDENCE = 0.5F
private const val DEFAULT_HAND_TRACKING_CONFIDENCE = 0.5F
private const val DEFAULT_HAND_PRESENCE_CONFIDENCE = 0.5F
private const val MP_RECOGNIZER_TASK = "gesture_recognizer.task"

class PLGestureRecognizerHelper(
    val context: Context,
    val onError: (String) -> Unit = { _: String -> },
    val onResults: (List<String>) -> Unit = {}
) {
    private val gestureRecognizer: GestureRecognizer by lazy { setupGestureRecognizer() }

    fun clearGestureRecognizer() {
        gestureRecognizer.close()
    }

    private fun setupGestureRecognizer(): GestureRecognizer {
        val baseOption = BaseOptions.builder()
            .setModelAssetPath(MP_RECOGNIZER_TASK)
            .setDelegate(Delegate.CPU)
            .build()
        val options = GestureRecognizer.GestureRecognizerOptions.builder()
            .setBaseOptions(baseOption)
            .setMinHandDetectionConfidence(DEFAULT_HAND_DETECTION_CONFIDENCE)
            .setMinTrackingConfidence(DEFAULT_HAND_TRACKING_CONFIDENCE)
            .setMinHandPresenceConfidence(DEFAULT_HAND_PRESENCE_CONFIDENCE)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setResultListener { results, _ ->
                val names = results.gestures().flatten().map { category -> category.categoryName() }
                onResults(names)
            }
            .setErrorListener { error ->
                onError(error.message ?: context.getString(R.string.pl_unknown_error))
            }
            .build()

        return GestureRecognizer.createFromOptions(context, options)
    }

    fun recognizeLiveStream(
        imageProxy: ImageProxy,
    ) {
        val frameTime = SystemClock.uptimeMillis()
        val bitmapBuffer = createBitmap(imageProxy.width, imageProxy.height)
        imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
        imageProxy.close()

        val matrix = Matrix().apply {
            postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
            postScale(
                -1f, 1f, imageProxy.width.toFloat(), imageProxy.height.toFloat()
            )
        }

        val rotatedBitmap = Bitmap.createBitmap(
            bitmapBuffer,
            0,
            0,
            bitmapBuffer.width,
            bitmapBuffer.height,
            matrix,
            true
        )

        val mpImage = BitmapImageBuilder(rotatedBitmap).build()
        gestureRecognizer.recognizeAsync(mpImage, frameTime)
    }
}