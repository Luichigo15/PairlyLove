package app.luichigo15.pairly.ui.role

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.common.ui.common.L15StateHandler
import app.luichigo15.pairly.R
import app.luichigo15.pairly.common.PLRoleConst
import app.luichigo15.pairly.ui.common.PLAlertDialog
import app.luichigo15.pairly.ui.common.PLLoadingDialog
import app.luichigo15.pairly.ui.role.model.PLRoleEvent
import app.luichigo15.pairly.ui.role.widget.PLCopyCodeField
import app.luichigo15.pairly.ui.role.widget.PLEnterCodeField
import app.luichigo15.pairly.ui.role.widget.PLRoleBackButton
import app.luichigo15.pairly.ui.role.widget.PLRoleContinueButton
import app.luichigo15.pairly.ui.role.widget.PLRoleSelected
import app.luichigo15.pairly.ui.role.widget.PLRoleSelection
import app.luichigo15.pairly.ui.theme.PLTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PLRoleScreen(
    onNavigateToHome: (String) -> Unit,
    modifier: Modifier = Modifier,
    roleViewModel: PLRoleViewModel = hiltViewModel()
) {
    val roleData by roleViewModel.roleData.collectAsStateWithLifecycle()
    val uiState by roleViewModel.uiState.collectAsStateWithLifecycle()
    val enterCodeState = rememberTextFieldState()
    var showSelection by remember { mutableStateOf(false) }
    val titlePadding by animateDpAsState(
        targetValue = if (showSelection) 10.dp else 100.dp,
        animationSpec = tween(1000)
    )
    val coroutineScope = rememberCoroutineScope()

    val onBackPressed = {
        if(showSelection){
            showSelection = false
            coroutineScope.launch {
                delay(1800)
                roleViewModel.onEvent(PLRoleEvent.ClearRole)
            }
            enterCodeState.clearText()
        }
    }

    BackHandler(showSelection) {
        onBackPressed()
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeContentPadding()
            .padding(15.dp),
    ) {
        val (rolesRef, titleRef, selectedRef, backBtn, copyCode, continueBtn) = createRefs()
        val guideLine = createGuidelineFromTop(0.5f)

        PLRoleBackButton(isVisible = showSelection, modifier = Modifier.constrainAs(backBtn) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }, onClick = onBackPressed)

        Text(
            stringResource(R.string.pl_role_title), modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = titlePadding),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface)
        PLRoleSelection(
            isVisible = !showSelection,
            modifier = Modifier.constrainAs(rolesRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onRoleSelected = {
                roleViewModel.onEvent(PLRoleEvent.RoleSelected(it))
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
        PLCopyCodeField(
            code = roleData.uuid,
            isVisible = showSelection && roleData.role == PLRoleConst.BOY_ROLE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .constrainAs(copyCode) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        PLEnterCodeField(
            isVisible = showSelection && roleData.role == PLRoleConst.GIRL_ROLE,
            state = enterCodeState,
            onValueChange = {
                roleViewModel.onEvent(PLRoleEvent.CodeChanged(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .constrainAs(copyCode) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        PLRoleContinueButton(
            isVisible = showSelection,
            enabled = roleData.checkValid(),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(continueBtn) {
                    bottom.linkTo(parent.bottom)
                }
                .padding(20.dp),
            onClick = {
                roleViewModel.onEvent(PLRoleEvent.Submit)
            })
    }

    L15StateHandler(state = uiState, onSuccess = {
        PLAlertDialog(onDismiss = { onNavigateToHome(roleData.role) }, message = R.string.pl_linked)
    }, onError = { error ->
        PLAlertDialog(onDismiss = {
            roleViewModel.onEvent(PLRoleEvent.ResetState)
        }, message = error.message, isSuccess = false)
    }, onLoading = {
        PLLoadingDialog()
    })
}

@Preview(showBackground = true)
@Composable
private fun PLRoleScreenPreview() {
    PLTheme(darkTheme = true) {
        PLRoleScreen({})
    }
}