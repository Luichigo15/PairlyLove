package app.luichigo15.pairly.domain.usecase.gift

import app.luichigo15.pairly.domain.repository.PLGiftRepository
import javax.inject.Inject

class PLObserveGiftsUseCase @Inject constructor(
    private val giftRepository: PLGiftRepository,
) {

    operator fun invoke() = giftRepository.observeGifts()

}