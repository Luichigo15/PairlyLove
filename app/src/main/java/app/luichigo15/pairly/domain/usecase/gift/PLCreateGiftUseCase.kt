package app.luichigo15.pairly.domain.usecase.gift

import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.repository.PLGiftRepository
import javax.inject.Inject

class PLCreateGiftUseCase @Inject constructor(
    private val giftRepository: PLGiftRepository
) {
    operator fun invoke(gift: PLGift) = giftRepository.createGift(gift)
}