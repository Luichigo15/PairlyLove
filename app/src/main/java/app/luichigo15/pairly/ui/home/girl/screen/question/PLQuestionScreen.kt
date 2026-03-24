package app.luichigo15.pairly.ui.home.girl.screen.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.common.ui.common.L15StateHandler
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLEmptyScreen
import app.luichigo15.pairly.ui.common.PLLoadingDialog
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.home.girl.screen.question.model.PLQuestionEvent
import app.luichigo15.pairly.ui.home.girl.screen.question.widget.PLQuestionCard

@Composable
fun PLQuestionScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    questionViewModel: PLQuestionViewModel = hiltViewModel()
) {
    val uiState by questionViewModel.uiState.collectAsStateWithLifecycle()

    L15StateHandler(
        state = uiState,
        onStart = {},
        onLoading = {
            PLLoadingDialog()
        }, onError = {
            PLEmptyScreen(modifier = Modifier.fillMaxSize(), message = R.string.pl_questions_error)
        }, onSuccess = { questions ->
            PLCarousel(onBack = onBack, questions = questions, onGenerateQuestions = {
                questionViewModel.onEvent(PLQuestionEvent.GenerateQuestions)
            }, modifier = modifier)
        })
}

@Composable
private fun PLCarousel(
    onBack: () -> Unit,
    onGenerateQuestions: () -> Unit,
    questions: List<String>, modifier: Modifier = Modifier
) {
    val visibleQuestions = remember { mutableStateListOf<String>().apply { addAll(questions) } }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PLTopBar(title = R.string.pl_questions, onBackClick = onBack)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (visibleQuestions.isEmpty()) Button(onClick = onGenerateQuestions) {
                Text(text = stringResource(R.string.pl_generate_more_questions))
            }
            else visibleQuestions.forEach { question ->
                PLQuestionCard(question = question, onSwiped = {
                    visibleQuestions.remove(question)
                })
            }
        }
    }
}