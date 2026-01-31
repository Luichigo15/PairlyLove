package app.luichigo15.pairly.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PLSplashScreen(
    onAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Black)) {
    }
}