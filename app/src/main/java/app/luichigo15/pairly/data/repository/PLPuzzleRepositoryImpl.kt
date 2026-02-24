package app.luichigo15.pairly.data.repository

import app.luichigo15.common.utils.L15Logger
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.data.api.service.PLPuzzleApi
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.model.PLPuzzle
import app.luichigo15.pairly.domain.repository.PLPuzzleRepository
import app.luichigo15.pairly.environment.PLEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class PLPuzzleRepositoryImpl @Inject constructor(
    private val puzzleApi: PLPuzzleApi,
    private val firestore: PLFirestore
) : PLPuzzleRepository {
    private val TAG = PLPuzzleRepositoryImpl::class.java.simpleName

    override suspend fun uploadImage(
        file: RequestBody,
        fileName: String
    ): L15Result<Boolean, PLErrorCodes> = withContext(Dispatchers.IO) {
        try {
            val filePart = MultipartBody.Part.createFormData("file", fileName, file)
            val presetPart =
                PLEnvironment.getPuzzlePreset().toRequestBody("text/plain".toMediaType())
            val fileNamePart = fileName.plus("_${System.currentTimeMillis()}")
                .toRequestBody("text/plain".toMediaType())

            val response = puzzleApi.uploadImage(filePart, presetPart, fileNamePart)
            if(response.url.isEmpty()) return@withContext L15Result.Error(PLErrorCodes.ERROR_UPLOADING_IMAGE)

            if (firestore.createPuzzle(response.toDomain(fileName))) L15Result.Success(true)
            else L15Result.Error(PLErrorCodes.ERROR_UPLOADING_IMAGE)
        } catch (e: Exception) {
            L15Logger.e(TAG, "uploadImage", "Error uploading image ${e.message}")
            L15Result.Error(PLErrorCodes.ERROR_UPLOADING_IMAGE)
        }
    }

    override fun observePuzzles(): Flow<List<PLPuzzle>> =
        firestore.observePuzzles().flowOn(Dispatchers.IO)
}