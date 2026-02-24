package app.luichigo15.pairly.ui.home.boy.screen.puzzle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.common.ui.common.L15StateHandler
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLAlertDialog
import app.luichigo15.pairly.ui.common.PLEmptyScreen
import app.luichigo15.pairly.ui.common.PLLoadingDialog
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.model.PLBoyPuzzleEvent
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.widget.PLPuzzleBottomSheet
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.widget.PlPuzzleGrid
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLBoyPuzzleScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    puzzleViewModel: PLBoyPuzzleViewModel = hiltViewModel()
) {
    val newPuzzle by puzzleViewModel.puzzleData.collectAsStateWithLifecycle()
    val uiState by puzzleViewModel.uiState.collectAsStateWithLifecycle()
    var showPuzzleBottomSheet by remember { mutableStateOf(false) }
    val puzzles by puzzleViewModel.puzzles.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            PLTopBar(title = R.string.pl_puzzles, onBackClick = onBack)
            if (puzzles.isNotEmpty()) PlPuzzleGrid(puzzles = puzzles)
            else PLEmptyScreen(message = R.string.pl_no_images_added, title = R.string.pl_puzzles)
        }
        FloatingActionButton(
            onClick = {
                puzzleViewModel.onEvent(PLBoyPuzzleEvent.ClearData)
                showPuzzleBottomSheet = true
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }

    if (showPuzzleBottomSheet) PLPuzzleBottomSheet(
        onDismiss = { showPuzzleBottomSheet = false },
        newPuzzle = newPuzzle,
        onEvent = puzzleViewModel::onEvent
    )

    L15StateHandler(state = uiState, onStart = {}, onLoading = {
        PLLoadingDialog()
    }, onError = { error ->
        PLAlertDialog(
            onDismiss = { puzzleViewModel.onEvent(PLBoyPuzzleEvent.ClearUiState) },
            message = error.message
        )
    }, onSuccess = {
        puzzleViewModel.onEvent(PLBoyPuzzleEvent.ClearUiState)
    })
}

@Preview(showBackground = true)
@Composable
private fun PLBoyPuzzleScreenPreview() {
    PLTheme(darkTheme = true) {
        PLBoyPuzzleScreen({})
    }
}