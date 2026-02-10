package app.luichigo15.pairly.ui.home.boy.screen.gift

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.home.boy.screen.gift.widget.PLDatePickerField
import app.luichigo15.pairly.ui.theme.PLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PLBoyGiftScreen(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val giftName = rememberTextFieldState()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(stringResource(R.string.pl_gifts), style = MaterialTheme.typography.headlineLarge)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
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
            PLDatePickerField(modifier = Modifier.fillMaxWidth())
            Button(onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.pl_continue))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLBoyGiftScreenPreview() {
    PLTheme(darkTheme = true) {
        PLBoyGiftScreen({})
    }
}