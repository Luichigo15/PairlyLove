package app.luichigo15.pairly.ui.home.boy.screen.puzzle.model

import android.content.Context
import android.net.Uri

sealed class PLBoyPuzzleEvent {
    data class NameChanged(val name: String) : PLBoyPuzzleEvent()
    data class UriChanged(val uri: Uri) : PLBoyPuzzleEvent()
    data class Submit(val context: Context) : PLBoyPuzzleEvent()
    data object ClearData : PLBoyPuzzleEvent()
    data object ClearUiState : PLBoyPuzzleEvent()
}