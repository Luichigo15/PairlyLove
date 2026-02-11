package app.luichigo15.pairly.domain.usecase.gift

import app.luichigo15.pairly.domain.repository.PLGiftRepository
import javax.inject.Inject

class PLRedeemGiftUseCase @Inject constructor(
    private val giftRepository: PLGiftRepository
) {
    suspend operator fun invoke(id: String) = giftRepository.redeemGift(id)
}