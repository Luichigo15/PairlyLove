package app.luichigo15.pairly.ui.home.girl.screen.puzzle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.pairly.domain.usecase.puzzle.PLObservePuzzlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PLGirlPuzzleViewModel @Inject constructor(observePuzzlesUseCase: PLObservePuzzlesUseCase) :
    ViewModel() {

    val puzzles = observePuzzlesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )
}