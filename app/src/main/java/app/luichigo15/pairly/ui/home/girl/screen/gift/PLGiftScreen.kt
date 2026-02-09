package app.luichigo15.pairly.ui.home.girl.screen.gift

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.ui.home.girl.screen.gift.widget.PLGiftCard
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLGiftScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeContentPadding()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(10){
            PLGiftCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLGiftScreenPreview() {
    PLTheme(darkTheme = true) {
        PLGiftScreen()
    }
}