package app.luichigo15.pairly.ui.home.boy.screen.puzzle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLEmptyScreen
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLBoyPuzzleScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            PLTopBar(title = R.string.pl_puzzles, onBackClick = onBack)
            PLEmptyScreen(message = R.string.pl_no_images_added, title = R.string.pl_puzzles)
        }
        FloatingActionButton(
            onClick = {},
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLBoyPuzzleScreenPreview() {
    PLTheme(darkTheme = true) {
        PLBoyPuzzleScreen({})
    }
}