package app.luichigo15.pairly.domain.usecase.gift

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.repository.PLGiftRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PLCreateGiftUseCase @Inject constructor(
    private val giftRepository: PLGiftRepository
) {
    operator fun invoke(gift: PLGift) = flow {
        emit(L15Result.Loading)
        emit(giftRepository.createGift(gift))
    }
}