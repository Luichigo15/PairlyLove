package app.luichigo15.pairly.domain.firebase

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.data.database.model.PLGiftEntity
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.model.PLPuzzle
import app.luichigo15.pairly.domain.model.PLUser
import kotlinx.coroutines.flow.Flow

interface PLFirestore {

    suspend fun createUser(user: PLUser): L15Result<Boolean, PLErrorCodes>

    suspend fun createGift(gift: PLGift): L15Result<Boolean, PLErrorCodes>

    fun getGifts(): Flow<List<PLGiftEntity>>

    suspend fun redeemGift(id: String): Boolean

    suspend fun updateNotificationsToken(token: String)

    suspend fun createPuzzle(puzzle: PLPuzzle): Boolean
}