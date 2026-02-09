package app.luichigo15.pairly.ui.home.girl.screen.gift.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme
import app.luichigo15.pairly.utils.rotateVertically

@Composable
fun PLGiftCard(modifier: Modifier = Modifier) {
    Box {
        Card(
            shape = PLCouponShape(),
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.onSecondaryContainer,
                        PLCouponShape()
                    )
                    .padding(16.dp)
                    .height(100.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PLLeftSide()
                PLDottedLine()
                PLRightSide(modifier = Modifier.weight(1f))
            }
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(PLCouponShape())
        ) {
            Icon(
                painter = painterResource(R.drawable.pl_ic_heart),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 40.dp)
                    .rotate(-20f)
            )
        }

        SmallFloatingActionButton(
            onClick = {},
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 12.dp, y = 12.dp)
        ) {
            Icon(Icons.Default.Redeem, contentDescription = null)
        }
    }
}

@Composable
private fun PLLeftSide(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp
                    )
                ) {
                    append("${stringResource(R.string.pl_gift_card_left_side_2)}\n")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                ) {
                    append(stringResource(R.string.pl_gift_card_left_side_1))
                }
            },
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.rotateVertically()
        )
    }
}

@Composable
private fun PLDottedLine(modifier: Modifier = Modifier) {
    val borderColor = MaterialTheme.colorScheme.onSecondaryContainer

    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .width(2.dp)
    ) {
        drawLine(
            color = borderColor,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = 4f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
        )
    }
}

@Composable
private fun PLRightSide(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(R.string.pl_gift_card_right_side_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            "UNA CITA ROMÁNTICA BAJO LAS ESTRELLAS",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 2
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PLGiftCardPreview() {
    PLTheme(darkTheme = true) {
        PLGiftCard()
    }
}