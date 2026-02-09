package app.luichigo15.pairly.ui.role.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import app.luichigo15.pairly.R

@Composable
fun PLRoleBackButton(
    isVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(1000)),
        exit = fadeOut(tween(1000))
    ) {
        OutlinedIconButton(
            onClick = onClick, border = IconButtonDefaults.outlinedIconButtonBorder(true).copy(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.onSurface,
                        MaterialTheme.colorScheme.onSurface
                    )
                )
            ), colors = IconButtonDefaults.outlinedIconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            ), modifier = modifier
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }
}

@Composable
fun PLRoleContinueButton(
    isVisible: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = fadeIn(tween(1000, delayMillis = 1200)),
        exit = fadeOut(tween(1000))
    ) {
        Button(
            enabled = enabled, onClick = onClick, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.pl_continue))
        }
    }
}