package app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
data class PLPuzzlePiece(
    val id: Int,
    val correctRow: Int,
    val correctCol: Int,
    val bitmap: ImageBitmap,
    val width: Float,
    val height: Float,
    val offset: Offset = Offset.Zero,
    val isPlaced: Boolean = false
)