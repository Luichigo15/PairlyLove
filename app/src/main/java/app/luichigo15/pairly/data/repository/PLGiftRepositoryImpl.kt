package app.luichigo15.pairly.data.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.repository.PLGiftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PLGiftRepositoryImpl @Inject constructor(
    private val firestore: PLFirestore,
) : PLGiftRepository {
    private val TAG = PLGiftRepositoryImpl::class.java.simpleName

    override fun createGift(gift: PLGift): Flow<L15Result<Boolean, PLErrorCodes>> =
        firestore.createGift(gift)

}