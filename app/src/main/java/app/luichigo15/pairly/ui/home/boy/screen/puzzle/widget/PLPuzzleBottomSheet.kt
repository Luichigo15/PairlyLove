package app.luichigo15.pairly.ui.home.boy.screen.puzzle.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.home.boy.common.PLBoyCommonTextField
import app.luichigo15.pairly.ui.theme.PLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PLPuzzleBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                stringResource(R.string.pl_puzzles),
                style = MaterialTheme.typography.headlineLarge
            )
            FilledTonalButton(onClick = {}, modifier = Modifier.align(Alignment.Start)) {
                Text(stringResource(R.string.pl_select_image))
            }
            PLBoyCommonTextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                label = R.string.pl_puzzle_name,
                icon = Icons.Outlined.Extension
            )
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.pl_upload))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLPuzzleBottomSheetPreview() {
    PLTheme(darkTheme = true) {
        PLPuzzleBottomSheet(onDismiss = {})
    }
}