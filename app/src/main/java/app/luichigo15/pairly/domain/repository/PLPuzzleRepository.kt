package app.luichigo15.pairly.domain.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import okhttp3.RequestBody
import java.io.File

interface PLPuzzleRepository {
    suspend fun uploadImage(file: RequestBody, fileName: String): L15Result<Boolean, PLErrorCodes>
}