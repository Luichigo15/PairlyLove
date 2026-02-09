package app.luichigo15.pairly.ui.home.girl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import app.luichigo15.pairly.ui.home.common.PLCommonHome
import app.luichigo15.pairly.ui.home.navigation.PLRoute

@Composable
fun PLGirlHomeScreen(modifier: Modifier = Modifier){
    val backStack = remember { mutableStateListOf<PLRoute>(PLRoute.Home) }

    NavDisplay(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeContentPadding()
            .padding(10.dp),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                PLRoute.Home -> NavEntry(key) {
                    PLCommonHome()
                }

                else -> NavEntry(key) {
                    PLCommonHome()
                }
            }
        })
}