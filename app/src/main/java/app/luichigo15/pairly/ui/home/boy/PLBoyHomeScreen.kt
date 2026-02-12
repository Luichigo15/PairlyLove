package app.luichigo15.pairly.ui.home.boy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import app.luichigo15.pairly.ui.home.boy.screen.gift.PLBoyGiftScreen
import app.luichigo15.pairly.ui.home.boy.screen.puzzle.PLBoyPuzzleScreen
import app.luichigo15.pairly.ui.home.common.home.PLCommonHome
import app.luichigo15.pairly.ui.home.navigation.PLRoute

@Composable
fun PLBoyHomeScreen(modifier: Modifier = Modifier){
    val backStack = remember { mutableStateListOf<PLRoute>(PLRoute.Home) }
    var showGiftBottomSheet by remember { mutableStateOf(false) }
    val onBack: () -> Unit = { backStack.removeLastOrNull() }
    val onNavigate: (route: PLRoute) -> Unit = { route ->
        if (route !is PLRoute.Gift) backStack.add(route)
        else showGiftBottomSheet = true
    }

    NavDisplay(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeContentPadding()
            .padding(10.dp),
        backStack = backStack,
        onBack = onBack,
        entryProvider = { key ->
            when (key) {
                PLRoute.Home -> NavEntry(key) {
                    PLCommonHome(onNavigate = onNavigate)
                }

                PLRoute.Puzzle -> NavEntry(key){
                    PLBoyPuzzleScreen(onBack = onBack)
                }

                else -> NavEntry(key) {
                    PLCommonHome({})
                }
            }
        })

    if (showGiftBottomSheet) PLBoyGiftScreen(onDismiss = { showGiftBottomSheet = false })
}