package app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model.PLGirlPuzzleEvent
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model.PLPuzzlePiece
import app.luichigo15.pairly.utils.helpers.PLPuzzleUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PLSelectedPuzzleViewModel.PLSelectedPuzzleViewModelFactory::class)
class PLSelectedPuzzleViewModel @AssistedInject constructor(
    @Assisted private val imageUrl: String
) : ViewModel() {

    private val _pieces = MutableStateFlow<List<PLPuzzlePiece>>(emptyList())
    val pieces = _pieces.asStateFlow()

    fun onEvent(event: PLGirlPuzzleEvent) {
        when (event) {
            is PLGirlPuzzleEvent.PiecePlaced -> placePiece(event.piece)
            is PLGirlPuzzleEvent.GeneratePieces -> generatePieces(event.context)
        }
    }

    private fun generatePieces(context: Context) {
        viewModelScope.launch {
            val puzzle = PLPuzzleUtils.generatePuzzle(context, imageUrl)
            _pieces.update {
                puzzle
            }
        }
    }

    private fun placePiece(placedPiece: PLPuzzlePiece) {
        _pieces.update {
            it.map { piece ->
                if (piece.id == placedPiece.id) {
                    piece.copy(isPlaced = true)
                } else piece
            }
        }
    }

    @AssistedFactory
    interface PLSelectedPuzzleViewModelFactory {
        fun create(imageUrl: String): PLSelectedPuzzleViewModel
    }
}