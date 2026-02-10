package app.luichigo15.pairly.ui.home.girl.screen.gift

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.home.girl.screen.gift.widget.PLCouponCard
import app.luichigo15.pairly.ui.home.girl.screen.gift.widget.PLCouponFilter
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLGirlGiftScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableIntStateOf(1) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PLTopBar(title = R.string.pl_gifts, onBackClick = onBack)
        PLCouponFilter(selectedFilter = selectedFilter, onFilterSelected = { selectedFilter = it })
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(10) {
                PLCouponCard()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLGirlGiftScreenPreview() {
    PLTheme(darkTheme = true) {
        PLGirlGiftScreen({})
    }
}