package app.luichigo15.pairly.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.luichigo15.common.ui.common.L15AlertDialogConfig
import app.luichigo15.common.ui.common.L15AlertDialogType
import app.luichigo15.common.ui.common.L15CustomAlertDialog
import app.luichigo15.pairly.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import app.luichigo15.common.R as L15R

@Composable
fun PLAlertDialog(
    onDismiss: () -> Unit,
    @StringRes message: Int,
    isSuccess: Boolean = true,
) {
    val (lottieRaw, type, title) = if (isSuccess)
        Triple(R.raw.heart, L15AlertDialogType.Success, L15R.string.l15_success_label)
    else
        Triple(R.raw.sad_heart, L15AlertDialogType.Error, L15R.string.l15_error_label)
    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRaw))
    val dialogConfig = L15AlertDialogConfig(
        onDismiss = onDismiss,
        title = title,
        message = message, isCancelable = false, type = type
    )

    L15CustomAlertDialog(config = dialogConfig, content = {
        LottieAnimation(
            composition = lottie,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(150.dp)
        )
    })
}