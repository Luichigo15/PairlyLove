package app.luichigo15.pairly.ui.home.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLTopBar(
    @StringRes title: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        OutlinedIconButton(
            onClick = onBackClick, border = IconButtonDefaults.outlinedIconButtonBorder(true).copy(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.onSurface,
                        MaterialTheme.colorScheme.onSurface
                    )
                )
            ), colors = IconButtonDefaults.outlinedIconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
        Text(
            stringResource(title), modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PLTopBarPreview() {
    PLTheme(darkTheme = true) {
        PLTopBar(R.string.pl_gifts, {})
    }
}