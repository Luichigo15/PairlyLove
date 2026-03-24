package app.luichigo15.pairly.ui.home.girl.screen.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.domain.usecase.question.PLGenerateQuestionsUseCase
import app.luichigo15.pairly.ui.home.girl.screen.question.model.PLQuestionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLQuestionViewModel @Inject constructor(
    private val generateQuestionsUseCase: PLGenerateQuestionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<L15Result<List<String>, Unit>>(L15Result.Start)
    val uiState = _uiState.asStateFlow()

    init {
        generateQuestions()
    }

    fun onEvent(event: PLQuestionEvent) {
        when (event) {
            PLQuestionEvent.GenerateQuestions -> generateQuestions()
        }
    }

    private fun generateQuestions() {
        viewModelScope.launch {
            generateQuestionsUseCase().collect {
                _uiState.value = it
            }
        }
    }
}