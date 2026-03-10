package app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.widget

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model.PLPuzzlePiece
import kotlin.math.roundToInt

@Composable
fun PLPuzzlePieceView(
    piece: PLPuzzlePiece,
    onPiecePlaced: (PLPuzzlePiece) -> Unit
) {
    val density = LocalDensity.current
    val widthDp = with(density) { piece.width.toDp() }
    val heightDp = with(density) { piece.height.toDp() }
    var offset by remember { mutableStateOf(piece.offset) }
    var isDragging by remember { mutableStateOf(false) }
    val animatedOffset by animateOffsetAsState(offset)

    Box(
        modifier = Modifier
            .size(widthDp, heightDp)
            .offset {
                IntOffset(
                    animatedOffset.x.roundToInt(),
                    animatedOffset.y.roundToInt()
                )
            }
            .zIndex(
                when {
                    isDragging -> 2f
                    !piece.isPlaced -> 1f
                    else -> 0f
                }
            )
            .graphicsLayer {
                scaleX = if (isDragging) 1.1f else 1f
                scaleY = if (isDragging) 1.1f else 1f
            }
            .pointerInput(piece.isPlaced) {
                if (!piece.isPlaced) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDrag = { change, drag ->
                            change.consume()
                            offset += drag
                        },
                        onDragEnd = {
                            isDragging = false
                            val correctX = piece.correctCol * widthDp.toPx()
                            val correctY = piece.correctRow * heightDp.toPx()
                            val correctOffset = Offset(correctX, correctY)
                            if ((offset - correctOffset).getDistance() < 50f) {
                                offset = correctOffset
                                onPiecePlaced(piece)
                            }
                        }
                    )
                }
            }
            .then(
                if (isDragging)
                    Modifier.border(
                        2.dp,
                        Color(0xFF4CAF50),
                    )
                else Modifier
            )
    ) {
        Image(
            bitmap = piece.bitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}