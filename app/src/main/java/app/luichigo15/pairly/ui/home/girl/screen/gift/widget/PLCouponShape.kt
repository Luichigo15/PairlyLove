package app.luichigo15.pairly.ui.home.girl.screen.gift.widget

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class PLCouponShape(
    private val cutRadius: Dp = 24.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val r = with(density) { cutRadius.toPx() }
        val base = Path().apply {
            addRect(Rect(0f, 0f, size.width, size.height))
        }

        val cuts = Path().apply {
            addOval(Rect(-r, -r, r, r))
            addOval(Rect(size.width - r, -r, size.width + r, r))
            addOval(Rect(-r, size.height - r, r, size.height + r))
            addOval(Rect(size.width - r, size.height - r, size.width + r, size.height + r))
        }

        val finalPath = Path.combine(
            PathOperation.Difference,
            base,
            cuts
        )

        return Outline.Generic(finalPath)
    }
}