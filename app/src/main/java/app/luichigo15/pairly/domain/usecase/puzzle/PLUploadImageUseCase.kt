package app.luichigo15.pairly.domain.usecase.puzzle

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.repository.PLPuzzleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

class PLUploadImageUseCase @Inject constructor(
    private val repository: PLPuzzleRepository
) {
    operator fun invoke(file: RequestBody, fileName: String): Flow<L15Result<Boolean, PLErrorCodes>> =
        flow {
            emit(L15Result.Loading)
            emit(repository.uploadImage(file, fileName))
        }
}