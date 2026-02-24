package app.luichigo15.pairly.ui.home.boy.screen.puzzle.model

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.Immutable

@Immutable
data class PLUploadPuzzle(
    val name: String = "",
    val uri: Uri? = null
) {
    fun checkValid() = name.isNotEmpty() && uri != null

    fun getFileName(context: Context): String {
        return uri?.let {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (cursor.moveToFirst()) cursor.getString(index) else "file"
            }
        } ?: "file"
    }
}
