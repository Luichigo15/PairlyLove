package app.luichigo15.pairly.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.luichigo15.common.ui.common.L15SimpleScreen
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun PLEmptyScreen(
    @StringRes message: Int,
    modifier: Modifier = Modifier,
    title: Int? = null
) {

    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
    L15SimpleScreen(
        modifier = modifier,
        message = message,
        title = title,
        content = {
            LottieAnimation(
                composition = lottie,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        })
}

@Preview(showBackground = true)
@Composable
private fun PLEmptyScreenPreview() {
    PLTheme(darkTheme = true) {
        PLEmptyScreen(R.string.pl_no_gifts)
    }
}