package app.luichigo15.pairly.domain.firebase

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.data.database.model.PLGiftEntity
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.model.PLUser
import kotlinx.coroutines.flow.Flow

interface PLFirestore {

    fun createUser(user: PLUser): Flow<L15Result<Boolean, PLErrorCodes>>

    fun createGift(gift: PLGift): Flow<L15Result<Boolean, PLErrorCodes>>

    fun listenToGifts(): Flow<List<PLGiftEntity>>

    suspend fun updateNotificationsToken(token: String)
}