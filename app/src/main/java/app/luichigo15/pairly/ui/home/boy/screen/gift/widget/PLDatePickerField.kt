package app.luichigo15.pairly.ui.home.boy.screen.gift.widget

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import app.luichigo15.pairly.R
import app.luichigo15.pairly.utils.extensions.PLDateUtils
import app.luichigo15.common.R as L15R

@Composable
fun PLDatePickerField(modifier: Modifier = Modifier) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    OutlinedTextField(
        value = selectedDate?.let { PLDateUtils.convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text(stringResource(R.string.pl_expires_on)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) showDatePicker = true
                }
            }
    )

    if (showDatePicker)
        PLDatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = { selectedDate = it }
        )

}

@Composable
private fun PLDatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    val today = System.currentTimeMillis()
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today
            }
        }
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(L15R.string.l15_ok_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(L15R.string.l15_cancel_label))
            }
        }
    ) {
        DatePicker(state = datePickerState, showModeToggle = false)
    }
}

