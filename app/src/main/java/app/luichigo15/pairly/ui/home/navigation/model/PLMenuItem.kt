package app.luichigo15.pairly.ui.home.navigation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.home.navigation.PLRoute

@Immutable
data class PLMenuItem(
    val route: PLRoute,
    @param:DrawableRes val icon: Int,
    @param:StringRes val title: Int
)

val homeMenuItems = listOf(
    PLMenuItem(
        route = PLRoute.Calendar,
        icon = R.drawable.pl_ic_calendar,
        title = R.string.pl_calendar
    ),
    PLMenuItem(
        route = PLRoute.Gift,
        icon = R.drawable.pl_ic_gift,
        title = R.string.pl_gifts
    ),
    PLMenuItem(
        route = PLRoute.Question,
        icon = R.drawable.pl_ic_quote,
        title = R.string.pl_questions
    ),
    PLMenuItem(
        route = PLRoute.Puzzle,
        icon = R.drawable.pl_ic_puzzle,
        title = R.string.pl_puzzles
    )
)