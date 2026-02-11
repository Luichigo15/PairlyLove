package app.luichigo15.pairly.ui.home.boy.screen.gift

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.common.ui.common.L15StateHandler
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.common.PLAlertDialog
import app.luichigo15.pairly.ui.common.PLLoadingDialog
import app.luichigo15.pairly.ui.home.boy.screen.gift.model.PLBoyGiftEvent
import app.luichigo15.pairly.ui.home.boy.screen.gift.widget.PLDatePickerField
import app.luichigo15.pairly.ui.home.boy.screen.gift.widget.PLGiftNameField
import app.luichigo15.pairly.ui.theme.PLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PLBoyGiftScreen(
    onDismiss: () -> Unit, modifier: Modifier = Modifier,
    giftViewModel: PLBoyGiftViewModel = hiltViewModel()
) {
    val giftData by giftViewModel.giftData.collectAsStateWithLifecycle()
    val uiState by giftViewModel.uiState.collectAsStateWithLifecycle()

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
            PLGiftNameField(modifier = Modifier.fillMaxWidth(), onValueChange = {
                giftViewModel.onEvent(PLBoyGiftEvent.NameChanged(it))
            })
            PLDatePickerField(modifier = Modifier.fillMaxWidth(), onDateSelected = {
                giftViewModel.onEvent(PLBoyGiftEvent.DateChanged(it ?: 0L))
            })
            Button(
                onClick = { giftViewModel.onEvent(PLBoyGiftEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                enabled = giftData.getValid()
            ) {
                Text(stringResource(R.string.pl_continue))
            }
        }
    }

    L15StateHandler(state = uiState, onSuccess = {
        PLAlertDialog(onDismiss = {
            giftViewModel.onEvent(PLBoyGiftEvent.ResetState)
            onDismiss()
        }, message = R.string.pl_gift_created)
    }, onError = { error ->
        PLAlertDialog(onDismiss = {
            giftViewModel.onEvent(PLBoyGiftEvent.ResetState)
        }, message = error.message, isSuccess = false)
    }, onLoading = {
        PLLoadingDialog()
    })
}

@Preview(showBackground = true)
@Composable
private fun PLBoyGiftScreenPreview() {
    PLTheme(darkTheme = true) {
        PLBoyGiftScreen({})
    }
}