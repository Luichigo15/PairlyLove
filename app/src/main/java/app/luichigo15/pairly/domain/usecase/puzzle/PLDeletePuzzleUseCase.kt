package app.luichigo15.pairly.domain.usecase.puzzle

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.repository.PLPuzzleRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PLDeletePuzzleUseCase @Inject constructor(
    private val puzzleRepository: PLPuzzleRepository
) {
    operator fun invoke(id: String) = flow {
        emit(L15Result.Loading)
        val result = puzzleRepository.deletePuzzle(id)
        if (result) emit(L15Result.Success(true))
        else emit(L15Result.Error(PLErrorCodes.ERROR_DELETING_PUZZLE))
    }
}