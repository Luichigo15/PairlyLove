package app.luichigo15.pairly.domain.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.model.PLGift
import kotlinx.coroutines.flow.Flow

interface PLGiftRepository {
    suspend fun createGift(gift: PLGift): L15Result<Boolean, PLErrorCodes>
    fun syncGifts(): Flow<Unit>
    fun observeGifts(): Flow<List<PLGift>>
    suspend fun redeemGift(id: String)
}