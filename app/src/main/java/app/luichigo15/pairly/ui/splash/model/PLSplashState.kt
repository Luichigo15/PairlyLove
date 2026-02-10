package app.luichigo15.pairly.ui.splash.model

import androidx.compose.runtime.Immutable

@Immutable
data class PLSplashState(
    val isAnimationFinished: Boolean = false,
    val role: String = "",
    val pairCode: String = ""
)