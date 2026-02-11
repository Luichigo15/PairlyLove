package app.luichigo15.pairly.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.content.FileProvider
import java.io.File

suspend fun GraphicsLayer.saveAsShareableFile(context: Context): Uri? {
    val bitmap = this.toImageBitmap().asAndroidBitmap()
    val path = context.externalCacheDir.toString().plus("/coupon-${System.currentTimeMillis()}.png")
    val file = File(path)
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
    }

    return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
}