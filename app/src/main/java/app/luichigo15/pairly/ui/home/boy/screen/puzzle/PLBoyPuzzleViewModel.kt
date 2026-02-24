package app.luichigo15.pairly.ui.home.boy.screen.puzzle

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.usecase.puzzle.PLDeletePuzzleUseCase
import app.luichigo15.pairly.domain.usecase.puzzle.PLObservePuzzlesUseCase
import app.luichigo15.pairly.domain.usecase.puzzle.PLUploadImageUseCase
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.model.PLBoyPuzzleEvent
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.model.PLUploadPuzzle
import app.luichigo15.pairly.utils.extensions.asRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLBoyPuzzleViewModel @Inject constructor(
    private val uploadImageUseCase: PLUploadImageUseCase,
    observePuzzlesUseCase: PLObservePuzzlesUseCase,
    private val deletePuzzleUseCase: PLDeletePuzzleUseCase
) : ViewModel() {

    private val _puzzleData = MutableStateFlow(PLUploadPuzzle())
    val puzzleData = _puzzleData.asStateFlow()

    private val _uiState = MutableStateFlow<L15Result<Boolean, PLErrorCodes>>(L15Result.Start)
    val uiState = _uiState.asStateFlow()

    val puzzles = observePuzzlesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    fun onEvent(event: PLBoyPuzzleEvent) {
        when (event) {
            is PLBoyPuzzleEvent.NameChanged -> _puzzleData.update { it.copy(name = event.name) }
            is PLBoyPuzzleEvent.Submit -> upload(event.context)
            is PLBoyPuzzleEvent.UriChanged -> _puzzleData.update { it.copy(uri = event.uri) }
            PLBoyPuzzleEvent.ClearData -> _puzzleData.update { PLUploadPuzzle() }
            PLBoyPuzzleEvent.ClearUiState -> _uiState.update { L15Result.Start }
            is PLBoyPuzzleEvent.Delete -> delete(event.id)
        }
    }

    private fun upload(context: Context) {
        viewModelScope.launch {
            uploadImageUseCase(
                _puzzleData.value.uri!!.asRequestBody(context, "image/*"),
                _puzzleData.value.name.trim().replace(" ", "_")
            ).collect { state ->
                _uiState.update { state }
            }
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            deletePuzzleUseCase(id).collect { state ->
                _uiState.update { state }
            }
        }
    }
}