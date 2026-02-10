package app.luichigo15.pairly.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PLDateUtils {

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}