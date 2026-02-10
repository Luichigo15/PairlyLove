package app.luichigo15.pairly.common

import androidx.annotation.StringRes
import app.luichigo15.pairly.R

enum class PLErrorCodes(@param:StringRes val message: Int) {
    UUID_DOES_NOT_EXIST(R.string.pl_code_does_not_exist),
    ERROR_CREATING_USER(R.string.pl_error_creating_user),
    ERROR_CREATING_GIFT(R.string.pl_error_creating_gift)
}