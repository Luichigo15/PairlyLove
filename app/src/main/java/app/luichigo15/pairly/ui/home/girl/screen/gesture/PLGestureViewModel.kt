package app.luichigo15.pairly.ui.home.girl.screen.gesture

import android.content.Context
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import app.luichigo15.common.utils.L15Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class PLGestureViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()
    private val _uiState = MutableStateFlow<L15Result<String, Unit>>(L15Result.Start)
    val uiState = _uiState.asStateFlow()

    private val gestureHelper = PLGestureRecognizerHelper(
        context = context,
        onError = {
            _uiState.update { L15Result.Error(Unit) }
        },
        onResults = { gesture ->
            if (gesture.isEmpty()) return@PLGestureRecognizerHelper

            _uiState.update { L15Result.Success(gesture.first()) }
        }
    )

    fun analyzeImage(image: ImageProxy) {
        gestureHelper.recognizeLiveStream(image)
    }

    fun getExecutor(): ExecutorService = executor

    override fun onCleared() {
        super.onCleared()

        gestureHelper.clearGestureRecognizer()
        executor.shutdown()
    }
}