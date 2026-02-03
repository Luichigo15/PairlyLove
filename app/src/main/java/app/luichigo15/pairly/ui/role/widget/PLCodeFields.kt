package app.luichigo15.pairly.ui.role.widget

import android.content.ClipData
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme
import kotlinx.coroutines.launch

private suspend fun copyToClipboard(text: String, clipboard: Clipboard, context: Context) {
    val clipData = ClipData.newPlainText("code", text)
    val clipEntry = ClipEntry(clipData)
    clipboard.setClipEntry(clipEntry)
    Toast.makeText(context, context.getString(R.string.pl_copied_code), Toast.LENGTH_SHORT).show()
}

private suspend fun pasteFromClipboard(clipboard: Clipboard): String? {
    return clipboard.getClipEntry()?.clipData?.getItemAt(0)?.text?.let {
        println(it)
        it.toString()
    }
}

@Composable
private fun PLCodeBox(
    isVisible: Boolean,
    content: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.onBackground
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(durationMillis = 1000, delayMillis = 1200)),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
                    )

                    drawRoundRect(
                        color = borderColor,
                        style = stroke,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
fun PLCopyCodeField(
    isVisible: Boolean,
    code: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    PLCodeBox(isVisible = isVisible, modifier = modifier, content = {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.pl_your_code),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                code, style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    copyToClipboard(code, clipboard, context)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FileCopy,
                    contentDescription = null,
                )
                Text(stringResource(R.string.pl_copy))
            }
        }
    })
}

@Composable
fun PLEnterCodeField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val fieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
        cursorColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
        focusedContainerColor = MaterialTheme.colorScheme.secondary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary
    )
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state) {
        snapshotFlow { state.text }.collect {
            onValueChange(it.toString())
        }
    }

    PLCodeBox(isVisible = isVisible, modifier = modifier, content = {
        OutlinedTextField(
            state = state, label = {
                Text(stringResource(R.string.pl_your_code))
            }, colors = fieldColors,
            lineLimits = TextFieldLineLimits.SingleLine,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            pasteFromClipboard(clipboard)?.let {
                                state.clearText()
                                state.setTextAndPlaceCursorAtEnd(it)
                            }
                        }
                    },
                    imageVector = Icons.Outlined.ContentPaste,
                    contentDescription = null,
                )
            }
        )
    })
}

@Preview(showBackground = true)
@Composable
private fun PLCopyCodeFieldPreview() {
    PLTheme(darkTheme = true) {
        PLCopyCodeField(true, "12345678")
    }
}

@Preview(showBackground = true)
@Composable
private fun PLEnterCodeFieldPreview() {
    PLTheme(darkTheme = true) {
        PLEnterCodeField(rememberTextFieldState(), {}, true)
    }
}