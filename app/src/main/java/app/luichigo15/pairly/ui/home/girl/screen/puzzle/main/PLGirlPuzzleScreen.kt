package app.luichigo15.pairly.ui.home.girl.screen.puzzle.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLEmptyScreen
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.home.common.puzzle.PlPuzzleGrid

@Composable
fun PLGirlPuzzleScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    puzzleViewModel: PLGirlPuzzleViewModel = hiltViewModel()
) {
    val puzzles by puzzleViewModel.puzzles.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize()) {
        PLTopBar(title = R.string.pl_puzzles, onBackClick = onBack)
        if (puzzles.isNotEmpty()) PlPuzzleGrid(puzzles = puzzles, onPuzzleClick = onNavigate)
        else PLEmptyScreen(message = R.string.pl_no_images_added, title = R.string.pl_puzzles)
    }
}