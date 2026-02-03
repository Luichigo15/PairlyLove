package app.luichigo15.pairly.ui.role

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.role.widget.PLRoleSelected
import app.luichigo15.pairly.ui.role.widget.PLRoleSelection
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLRoleScreen(modifier: Modifier = Modifier) {
    var showSelection by remember { mutableStateOf(false) }
    val titlePadding by animateDpAsState(
        targetValue = if (showSelection) 10.dp else 100.dp,
        animationSpec = tween(1000)
    )

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding(),
    ) {
        val (rolesRef, titleRef, selectedRef) = createRefs()
        val guideLine = createGuidelineFromTop(0.5f)

        Text(
            stringResource(R.string.pl_role_title), modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(top = titlePadding),
            style = MaterialTheme.typography.headlineLarge)
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