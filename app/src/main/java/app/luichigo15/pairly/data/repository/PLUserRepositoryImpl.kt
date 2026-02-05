package app.luichigo15.pairly.data.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.firebase.PLPushNotifications
import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.repository.PLUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PLUserRepositoryImpl @Inject constructor(
    private val firestore: PLFirestore,
    private val pushNotification: PLPushNotifications
) : PLUserRepository {
    private val TAG = PLUserRepositoryImpl::class.java.simpleName

    override fun createUser(user: PLUser): Flow<L15Result<Boolean, PLErrorCodes>> = flow {
        val token = pushNotification.getToken() ?: ""
        emitAll(firestore.createUser(user.copy(notificationsToken = token)))
    }
}