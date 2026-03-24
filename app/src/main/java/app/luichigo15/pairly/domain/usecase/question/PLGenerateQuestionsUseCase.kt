package app.luichigo15.pairly.domain.usecase.question

import app.luichigo15.pairly.domain.repository.PLQuestionRepository
import javax.inject.Inject

class PLGenerateQuestionsUseCase @Inject constructor(
    private val questionRepository: PLQuestionRepository
) {

    operator fun invoke() = questionRepository.generateQuestions()
}