package app.luichigo15.pairly.ui.home.common.home

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.luichigo15.common.ui.utils.L15PermissionRequester
import app.luichigo15.pairly.ui.home.common.home.model.PLHomeEvent
import app.luichigo15.pairly.ui.home.common.home.widget.PLHomeMenuCard
import app.luichigo15.pairly.ui.home.navigation.PLRoute
import app.luichigo15.pairly.ui.home.navigation.model.homeMenuItems
import app.luichigo15.pairly.ui.theme.PLTheme

private fun getEnterTransition(i: Int) = slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(
        durationMillis = 700,
        delayMillis = i * 180, easing = FastOutSlowInEasing
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = 600,
        delayMillis = i * 180
    )
)
@Composable
fun PLCommonHome(
    onNavigate: (route: PLRoute) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: PLHomeViewModel = hiltViewModel()
) {
    var startAnimation by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        L15PermissionRequester(
            context = context,
            permission = Manifest.permission.POST_NOTIFICATIONS
        ) { isGranted ->
            homeViewModel.onEvent(PLHomeEvent.RequestNotificationPermission(isGranted))
        }
    }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        OutlinedIconButton(
            onClick = { }, border = IconButtonDefaults.outlinedIconButtonBorder(true).copy(
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
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = homeMenuItems) { i, option ->
                AnimatedVisibility(
                    visible = startAnimation,
                    enter = getEnterTransition(i)
                ) {
                    PLHomeMenuCard(
                        onClick = { onNavigate(option.route) },
                        icon = option.icon,
                        title = option.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLCommonHomePreview() {
    PLTheme(darkTheme = true) {
        PLCommonHome({})
    }
}