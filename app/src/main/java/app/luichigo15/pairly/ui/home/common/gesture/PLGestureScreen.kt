package app.luichigo15.pairly.ui.home.common.gesture

import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.common.ui.common.L15StateHandler
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLAlertDialog
import app.luichigo15.pairly.ui.home.common.PLTopBar
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PLGestureScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    gestureViewModel: PLGestureViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
    val surfaceRequest by surfaceRequests.collectAsState()
    val uiState by gestureViewModel.uiState.collectAsStateWithLifecycle()
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val showGestureInfo by gestureViewModel.showGestureInfo.collectAsStateWithLifecycle()

    var gestureDetected by remember { mutableStateOf("") }
    val lottie by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            getLottieByGesture(gestureDetected)
        )
    )

    LaunchedEffect(Unit) {
        cameraProvider = ProcessCameraProvider.awaitInstance(context)
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                surfaceRequests.value = request
            }
        }
        val imageAnalyzer = ImageAnalysis.Builder()
            .setResolutionSelector(
                ResolutionSelector.Builder().setAspectRatioStrategy(
                    AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY
                ).build()
            ).setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .build()
            .also {
                it.setAnalyzer(gestureViewModel.getExecutor()) { image ->
                    gestureViewModel.analyzeImage(image)
                }
            }

        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_FRONT_CAMERA,
            preview, imageAnalyzer
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PLTopBar(title = R.string.pl_gestures, onBackClick = onBack)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            surfaceRequest?.let { request ->
                CameraXViewfinder(
                    surfaceRequest = request,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            RoundedCornerShape(10.dp)
                        )
                )
            }
            L15StateHandler(
                state = uiState,
                onStart = {},
                onLoading = {},
                onError = {},
                onSuccess = { gesture ->
                    gestureDetected = gesture
                    LottieAnimation(
                        composition = lottie,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(15.dp)
                            .align(Alignment.Center)
                    )
                }
            )
        }
    }

    if (showGestureInfo) PLAlertDialog(
        onDismiss = { gestureViewModel.setGestureInfo() },
        message = R.string.pl_gesture_info
    )
}

private fun getLottieByGesture(name: String): Int {
    return when (name) {
        "Thumb_Up" -> R.raw.thumb_up_gesture
        "Thumb_Down" -> R.raw.thumb_down_gesture
        "Victory" -> R.raw.winner_gesture
        "Pointing_Up" -> R.raw.up_gesture
        "Closed_Fist" -> R.raw.fist_gesture
        "Open_Palm" -> R.raw.palm_gesture
        "ILoveYou" -> R.raw.love_gesture
        else -> R.raw.heart
    }
}