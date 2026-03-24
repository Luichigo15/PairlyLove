package app.luichigo15.pairly.ui.home.girl.screen.question.model

sealed class PLQuestionEvent {
    data object GenerateQuestions : PLQuestionEvent()
}