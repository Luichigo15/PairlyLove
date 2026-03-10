package app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model

import android.content.Context
import android.graphics.Bitmap

sealed class PLGirlPuzzleEvent {
    data class GeneratePieces(val context: Context) : PLGirlPuzzleEvent()
    data class PiecePlaced(val piece: PLPuzzlePiece) : PLGirlPuzzleEvent()
}