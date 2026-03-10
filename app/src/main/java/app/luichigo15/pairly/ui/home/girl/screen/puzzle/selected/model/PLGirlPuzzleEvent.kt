package app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model

import android.content.Context

sealed class PLGirlPuzzleEvent {
    data class GeneratePieces(val context: Context, val maxWidth:Int, val maxHeight:Int) : PLGirlPuzzleEvent()
    data class PiecePlaced(val piece: PLPuzzlePiece) : PLGirlPuzzleEvent()
}