package app.luichigo15.pairly.domain.repository

import app.luichigo15.common.utils.L15Result
import kotlinx.coroutines.flow.Flow

interface PLQuestionRepository {
    fun generateQuestions(): Flow<L15Result<List<String>, Unit>>
}