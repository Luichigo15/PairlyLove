package app.luichigo15.pairly.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PLPuzzle(
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
)
