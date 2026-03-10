package app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLAlertDialog
import app.luichigo15.pairly.ui.common.PLLoadingDialog
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.widget.PLPuzzleBoard
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model.PLGirlPuzzleEvent

@Composable
fun PLSelectedPuzzleScreen(
    imageUrl: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    selectedPuzzleViewModel: PLSelectedPuzzleViewModel = hiltViewModel<PLSelectedPuzzleViewModel, PLSelectedPuzzleViewModel.PLSelectedPuzzleViewModelFactory> {
        it.create(imageUrl)
    }
) {
    val context = LocalContext.current
    val pieces by selectedPuzzleViewModel.pieces.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        selectedPuzzleViewModel.onEvent(PLGirlPuzzleEvent.GeneratePieces(context))
    }

    Column(modifier = modifier.fillMaxSize()) {
        PLTopBar(title = R.string.pl_puzzles, onBackClick = onBack)
        if (pieces.isEmpty()) PLLoadingDialog()
        else Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PLPuzzleBoard(
                pieces = pieces,
                onPiecePlaced = { selectedPuzzleViewModel.onEvent(PLGirlPuzzleEvent.PiecePlaced(it)) })
        }
    }

    if(pieces.isNotEmpty() && pieces.all { it.isPlaced })
        PLAlertDialog(onDismiss = onBack, message = R.string.pl_puzzle_completed)
}