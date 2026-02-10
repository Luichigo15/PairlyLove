package app.luichigo15.pairly.ui.home.boy.screen.gift.widget

import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.luichigo15.pairly.R

@Composable
fun PLGiftNameField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val giftName = rememberTextFieldState()
    LaunchedEffect(giftName) {
        snapshotFlow { giftName.text }.collect {
            onValueChange(it.toString())
        }
    }

    OutlinedTextField(
        modifier = modifier,
        state = giftName, label = {
            Text(stringResource(R.string.pl_gift_name))
        }, lineLimits = TextFieldLineLimits.SingleLine,
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.CardGiftcard,
                contentDescription = null,
            )
        }
    )
}