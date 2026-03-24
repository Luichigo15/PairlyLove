package app.luichigo15.pairly.data.firebase

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.domain.firebase.PLFirebaseAi
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PLFirebaseAiImpl @Inject constructor() : PLFirebaseAi {

    private val TAG = PLFirebaseAiImpl::class.java.simpleName
    private val defaultPrompt =
        "Write 5 questions to ask to my girlfriend about our relationship just to spend time as a mini game, separated by commas without any explanation or description, just the question because i want to show them in cards"

    private val model by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-3-flash-preview")
    }

    override fun generateQuestions(): Flow<L15Result<List<String>, Unit>> = flow {
        emit(L15Result.Loading)
        val response = model.generateContent(defaultPrompt)
        if (response.text.isNullOrEmpty()) emit(L15Result.Error(Unit))
        else emit(L15Result.Success(response.text!!.split(",")))
    }
}