package app.luichigo15.pairly.ui.role.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.luichigo15.pairly.R
import app.luichigo15.pairly.common.PLRoleConst

private var enterAnimation = expandHorizontally(
    tween(durationMillis = 800, delayMillis = 500),
    expandFrom = Alignment.CenterHorizontally
) + fadeIn(tween(1000))

private var exitAnimation = shrinkHorizontally(
    tween(durationMillis = 800),
    shrinkTowards = Alignment.CenterHorizontally
) + fadeOut(tween(durationMillis = 800))

@Composable
fun PLRoleSelection(
    isVisible: Boolean,
    onRoleSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = enterAnimation,
        exit = exitAnimation
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(), maxItemsInEachRow = 2, maxLines = 1
        ) {
            PLRoleCard(
                roleName = R.string.pl_boy_role,
                lottie = R.raw.boy,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f),
                onClick = { onRoleSelected(PLRoleConst.BOY_ROLE) }
            )
            PLRoleCard(
                roleName = R.string.pl_girl_role,
                lottie = R.raw.girl,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f),
                onClick = { onRoleSelected(PLRoleConst.GIRL_ROLE) }
            )
        }
    }
}

private var roleEnterAnimation = slideInVertically(
    tween(durationMillis = 800, delayMillis = 500), initialOffsetY = { it },
) + fadeIn(tween(durationMillis = 1800))

private var roleExitAnimation =
    slideOutVertically(tween(800), targetOffsetY = { it })+ fadeOut(tween(1800))

@Composable
fun PLRoleSelected(isVisible: Boolean, selectedRole: String, modifier: Modifier = Modifier) {
    val (lottie, title) = if (selectedRole == PLRoleConst.BOY_ROLE)
        Pair(R.raw.boy, R.string.pl_boy_role)
    else Pair(R.raw.girl, R.string.pl_girl_role)

    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier, enter = roleEnterAnimation,
        exit = roleExitAnimation
    ) {
        PLRoleCard(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f),
            roleName = title,
            lottie = lottie
        )
    }
}