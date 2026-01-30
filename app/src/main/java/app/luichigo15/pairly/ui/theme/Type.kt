package app.luichigo15.pairly.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import app.luichigo15.common.ui.utils.L15TypeHelper
import app.luichigo15.pairly.R

private val defaultFont = FontFamily(
    Font(R.font.baloo2_regular),
    Font(R.font.baloo2_bold, FontWeight.Bold),
    Font(R.font.baloo2_regular, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.baloo2_bold, FontWeight.Bold, FontStyle.Italic)
)

val PLTypography = L15TypeHelper.generateType(defaultFont)