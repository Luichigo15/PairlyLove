package app.luichigo15.pairly.ui.home.boy.common

import androidx.annotation.StringRes
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun PLBoyCommonTextField(
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val state = rememberTextFieldState()
    LaunchedEffect(state) {
        snapshotFlow { state.text }.collect {
            onValueChange(it.toString())
        }
    }

    OutlinedTextField(
        modifier = modifier,
        state = state, label = {
            Text(stringResource(label))
        }, lineLimits = TextFieldLineLimits.SingleLine,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        }
    )
}