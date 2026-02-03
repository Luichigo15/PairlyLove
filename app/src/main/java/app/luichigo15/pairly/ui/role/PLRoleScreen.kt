package app.luichigo15.pairly.ui.role

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.role.widget.PLRoleCard
import app.luichigo15.pairly.ui.role.widget.PLRoleSelected
import app.luichigo15.pairly.ui.role.widget.PLRoleSelection
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLRoleScreen(modifier: Modifier = Modifier) {
    var showSelection by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding(),
    ) {
        val (rolesRef, titleRef, selectedRef) = createRefs()
        val guideLine = createGuidelineFromTop(0.5f)

        PLRoleSelection(
            isVisible = !showSelection,
            modifier = Modifier.constrainAs(rolesRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onRoleSelected = {
                showSelection = true
            })

        PLRoleSelected(
            isVisible = showSelection,
            modifier = Modifier.constrainAs(selectedRef) {
                top.linkTo(parent.top)
                bottom.linkTo(guideLine)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, onClickListener = {
                showSelection = false
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PLRoleScreenPreview() {
    PLTheme(darkTheme = true) {
        PLRoleScreen()
    }
}