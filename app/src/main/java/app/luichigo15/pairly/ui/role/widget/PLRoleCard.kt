package app.luichigo15.pairly.ui.role.widget

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.theme.PLTheme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun PLRoleCard(
    @StringRes roleName: Int,
    @RawRes lottie: Int,
    modifier: Modifier = Modifier
) {
    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie))

    ElevatedCard(
        onClick = {},
        modifier = modifier
            .padding(10.dp),
    ) {
        LottieAnimation(
            composition = lottie,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            stringResource(roleName),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PLRoleCardPreview() {
    PLTheme {
        PLRoleCard(R.string.pl_boy_role, R.raw.boy)
    }
}