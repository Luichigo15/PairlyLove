package app.luichigo15.pairly.data.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.data.database.dao.PLGiftDao
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.repository.PLGiftRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PLGiftRepositoryImpl @Inject constructor(
    private val firestore: PLFirestore,
    private val giftDao: PLGiftDao
) : PLGiftRepository {
    private val TAG = PLGiftRepositoryImpl::class.java.simpleName

    override fun createGift(gift: PLGift): Flow<L15Result<Boolean, PLErrorCodes>> =
        firestore.createGift(gift)

    override fun syncGifts(): Flow<Unit> =
        firestore.listenToGifts()
            .onEach { gifts ->
                giftDao.upsertAll(gifts)
            }.map { }
            .flowOn(Dispatchers.IO)

    override fun observeGifts(): Flow<List<PLGift>> = giftDao.observeGifts().map { gifts ->
        gifts.map { it.toDomain() }
    }
}