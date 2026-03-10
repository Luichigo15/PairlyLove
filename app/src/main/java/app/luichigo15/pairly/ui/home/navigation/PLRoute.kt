package app.luichigo15.pairly.ui.home.navigation

sealed class PLRoute {
    object Home : PLRoute()
    object Gift : PLRoute()
    object Question : PLRoute()
    object Puzzle : PLRoute()
    object Calendar : PLRoute()
    data class SelectedPuzzle(val imageUrl: String) : PLRoute()
}