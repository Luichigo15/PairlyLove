package app.luichigo15.pairly.ui.home.girl.screen.gift.widget

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme

private val filters =
    listOf(R.string.pl_all_filter, R.string.pl_valid_filter, R.string.pl_expired_filter)

@Composable
fun PLCouponFilter(
    selectedFilter: Int,
    onFilterSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        filters.forEachIndexed { i, filter ->
            SegmentedButton(
                selected = selectedFilter == i,
                label = { Text(stringResource(filter)) },
                onClick = { onFilterSelected(i) },
                shape = SegmentedButtonDefaults.itemShape(i, filters.size)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLCouponFilterPreview() {
    PLTheme(darkTheme = true) {
        PLCouponFilter(selectedFilter = 1, onFilterSelected = {})
    }
}
