package app.luichigo15.pairly.ui.home.boy.screen.puzzle.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model.PLPuzzlePiece
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.widget.PLPuzzlePieceView
import app.luichigo15.pairly.utils.helpers.DEFAULT_COLS_ROWS

@Composable
fun PLPuzzleBoard(
    pieces: List<PLPuzzlePiece>,
    onPiecePlaced: (PLPuzzlePiece) -> Unit,
    rows: Int = DEFAULT_COLS_ROWS,
    cols: Int = DEFAULT_COLS_ROWS
) {
    val boardColor = MaterialTheme.colorScheme.onBackground
    val density = LocalDensity.current
    val pieceWidth = pieces.first().width
    val pieceHeight = pieces.first().height

    val boardWidthPx = pieceWidth * cols
    val boardHeightPx = pieceHeight * rows

    Box(
        modifier = Modifier.requiredSize(
            with(density) { boardWidthPx.toDp() },
            with(density) { boardHeightPx.toDp() }
        )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {

            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    drawRect(
                        color = boardColor,
                        topLeft = Offset(
                            col * pieceWidth,
                            row * pieceHeight
                        ),
                        size = Size(pieceWidth, pieceHeight),
                        style = Stroke(width = 3f)
                    )
                }
            }
        }

        pieces.forEach {
            PLPuzzlePieceView(piece = it, onPiecePlaced = onPiecePlaced)
        }
    }
}