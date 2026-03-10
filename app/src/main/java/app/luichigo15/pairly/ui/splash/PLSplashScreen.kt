package app.luichigo15.pairly.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun PLSplashScreen(
    onAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier) {
    val heartLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.heart))
    var showTitle by remember { mutableStateOf(false) }
    val showLogo = remember { MutableTransitionState(false) }

    LaunchedEffect(Unit) {
        delay(500)
        showTitle = true
        delay(1200)
        showLogo.targetState = true
    }

    LaunchedEffect(showLogo.isIdle) {
        if (showLogo.isIdle && showLogo.currentState) onAnimationFinished()
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        val (heartRef, titleRef, logoRef) = createRefs()

        LottieAnimation(
            composition = heartLottie,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .constrainAs(heartRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        AnimatedVisibility(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(heartRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, visible = showTitle,
            enter = expandHorizontally(tween(1200), expandFrom = Alignment.CenterHorizontally)
        ) {
            PLSplashTitle()
        }

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(logoRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visibleState = showLogo,
            enter = fadeIn(animationSpec = tween(1000))
        ) {
            Image(
                modifier = Modifier.alpha(0.7f),
                painter = painterResource(R.drawable.pl_ic_logo),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun PLSplashTitle(modifier: Modifier = Modifier) {
    val appName = stringResource(R.string.pl_app_name)

    Text(
        appName,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 60.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.lily))
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PLSplashScreenPreview() {
    PLTheme {
        PLSplashScreen({})
    }
}