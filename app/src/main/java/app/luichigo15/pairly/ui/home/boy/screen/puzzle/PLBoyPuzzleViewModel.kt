package app.luichigo15.pairly.ui.home.boy.screen.puzzle

import androidx.lifecycle.ViewModel
import app.luichigo15.pairly.domain.usecase.puzzle.PLUploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PLBoyPuzzleViewModel @Inject constructor(
    private val uploadImageUseCase: PLUploadImageUseCase
) : ViewModel() {

}