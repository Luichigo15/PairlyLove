package app.luichigo15.pairly.ui.home.common.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.domain.model.PLPuzzle
import app.luichigo15.pairly.ui.theme.PLTheme
import coil.compose.AsyncImage

@Composable
fun PlPuzzleGrid(
    puzzles: List<PLPuzzle>,
    modifier: Modifier = Modifier,
    isBoySide: Boolean = false,
    onDelete: (id: String) -> Unit = {},
    onPuzzleClick: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = puzzles, key = { it.id }) { puzzle ->
            PLPuzzleItem(
                puzzle = puzzle,
                onDelete = onDelete,
                isBoySide = isBoySide,
                onPuzzleClick = onPuzzleClick)
        }
    }
}

@Composable
private fun PLPuzzleItem(
    puzzle: PLPuzzle,
    onDelete: (id: String) -> Unit,
    onPuzzleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isBoySide: Boolean = false
) {
    val gradientStops = arrayOf(
        0f to Color.Transparent,
        1f to Color.Black.copy(alpha = 0.65f)
    )

    ElevatedCard(
        onClick = { onPuzzleClick(puzzle.imageUrl) },
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = puzzle.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colorStops = gradientStops))
            )
            if (isBoySide) OutlinedIconButton(
                onClick = { onDelete(puzzle.id) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
            Text(
                puzzle.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PlPuzzleGridPreview() {
    PLTheme(darkTheme = true) {
        PlPuzzleGrid(listOf(), onDelete = {}, isBoySide = true)
    }
}