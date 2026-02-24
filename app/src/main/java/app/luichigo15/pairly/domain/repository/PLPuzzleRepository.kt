package app.luichigo15.pairly.domain.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.model.PLPuzzle
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface PLPuzzleRepository {
    suspend fun uploadImage(file: RequestBody, fileName: String): L15Result<Boolean, PLErrorCodes>
    fun observePuzzles(): Flow<List<PLPuzzle>>
}