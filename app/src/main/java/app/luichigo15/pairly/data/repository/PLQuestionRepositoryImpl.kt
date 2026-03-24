package app.luichigo15.pairly.data.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.domain.firebase.PLFirebaseAi
import app.luichigo15.pairly.domain.repository.PLQuestionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PLQuestionRepositoryImpl @Inject constructor(
    private val firebaseAi: PLFirebaseAi
) : PLQuestionRepository {

    override fun generateQuestions(): Flow<L15Result<List<String>, Unit>> = firebaseAi.generateQuestions()

}