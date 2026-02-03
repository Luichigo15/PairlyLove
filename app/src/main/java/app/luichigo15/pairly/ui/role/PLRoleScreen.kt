package app.luichigo15.pairly.ui.role

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.role.model.PLRoleEvent
import app.luichigo15.pairly.ui.role.widget.PLRoleBackButton
import app.luichigo15.pairly.ui.role.widget.PLRoleSelected
import app.luichigo15.pairly.ui.role.widget.PLRoleSelection
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLRoleScreen(
    modifier: Modifier = Modifier,
    roleViewModel: PLRoleViewModel = hiltViewModel()
) {
    val roleData by roleViewModel.roleData.collectAsStateWithLifecycle()
    var showSelection by remember { mutableStateOf(false) }
    val titlePadding by animateDpAsState(
        targetValue = if (showSelection) 10.dp else 100.dp,
        animationSpec = tween(1000)
    )

    BackHandler(showSelection) {
        if (showSelection) showSelection = false
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(15.dp),
    ) {
        val (rolesRef, titleRef, selectedRef, backBtn) = createRefs()
        val guideLine = createGuidelineFromTop(0.5f)

        PLRoleBackButton(isVisible = showSelection, modifier = Modifier.constrainAs(backBtn) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }, onClick = {
            showSelection = false
            roleViewModel.onRoleEvent(PLRoleEvent.ClearRole)
        })

        Text(
            stringResource(R.string.pl_role_title), modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(top = titlePadding),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground)
        PLRoleSelection(
            isVisible = !showSelection,
            modifier = Modifier.constrainAs(rolesRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onRoleSelected = {
                roleViewModel.onRoleEvent(PLRoleEvent.RoleSelected(it))
                showSelection = true
            })
        PLRoleSelected(
            isVisible = showSelection,
            modifier = Modifier.constrainAs(selectedRef) {
                top.linkTo(parent.top)
                bottom.linkTo(guideLine)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, selectedRole = roleData.role
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