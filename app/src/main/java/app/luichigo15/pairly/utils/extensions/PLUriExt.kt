package app.luichigo15.pairly.utils.extensions

import android.content.Context
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

fun Uri.asRequestBody(context: Context, mimeType: String): RequestBody {
    return object : RequestBody() {

        override fun contentType(): MediaType? = mimeType.toMediaTypeOrNull()

        override fun writeTo(sink: BufferedSink) {
            context.contentResolver.openInputStream(this@asRequestBody)?.use { input ->
                sink.writeAll(input.source())
            }
        }
    }
}