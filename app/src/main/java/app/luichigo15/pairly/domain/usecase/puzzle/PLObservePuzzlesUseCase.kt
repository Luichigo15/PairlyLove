package app.luichigo15.pairly.domain.usecase.puzzle

import app.luichigo15.pairly.domain.repository.PLPuzzleRepository
import javax.inject.Inject

class PLObservePuzzlesUseCase @Inject constructor(
    private val puzzleRepository: PLPuzzleRepository
) {
    operator fun invoke() = puzzleRepository.observePuzzles()
}