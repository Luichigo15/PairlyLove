package app.luichigo15.pairly.domain.firebase

import app.luichigo15.common.utils.L15Result
import kotlinx.coroutines.flow.Flow

interface PLFirebaseAi {

    fun generateQuestions(): Flow<L15Result<List<String>, Unit>>
}