package app.luichigo15.pairly.ui.home.common.question.model

sealed class PLQuestionEvent {
    data object GenerateQuestions : PLQuestionEvent()
}