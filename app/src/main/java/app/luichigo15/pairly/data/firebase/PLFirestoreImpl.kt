package app.luichigo15.pairly.data.firebase

import app.luichigo15.common.utils.L15Logger
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.common.PLFirebaseConst
import app.luichigo15.pairly.common.PLRoleConst
import app.luichigo15.pairly.data.database.model.PLGiftEntity
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.provider.PLUserDataProvider
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class PLFirestoreImpl @Inject constructor(
    private val userDataProvider: PLUserDataProvider
) : PLFirestore {
    private val TAG = PLFirestoreImpl::class.java.simpleName

    private val firestore by lazy { Firebase.firestore }


    private suspend fun checkUuidValid(uuid: String): Boolean {
        return try {
            firestore.collection(PLFirebaseConst.USERS_NODE)
                .document(uuid)
                .collection(PLFirebaseConst.ROLES_NODE)
                .document(PLRoleConst.BOY_ROLE)
                .get().await().exists()
        } catch (e: Exception) {
            L15Logger.e(TAG, "checkUuidValid", "Error checking uuid $e", e)
            false
        }
    }

    override fun createUser(user: PLUser): Flow<L15Result<Boolean, PLErrorCodes>> = flow {
        emit(L15Result.Loading)
        if (user.role == PLRoleConst.GIRL_ROLE && !checkUuidValid(user.uuid)) {
            emit(L15Result.Error(PLErrorCodes.UUID_DOES_NOT_EXIST))
            return@flow
        }

        val result = suspendCancellableCoroutine { cont ->
            firestore.collection(PLFirebaseConst.USERS_NODE)
                .document(user.uuid)
                .collection(PLFirebaseConst.ROLES_NODE)
                .document(user.role)
                .set(user)
                .addOnSuccessListener {
                    cont.resume(
                        L15Result.Success(true),
                        onCancellation = { _, _, _ -> }
                    )
                }
                .addOnFailureListener {
                    L15Logger.e(TAG, "createUser", "Error creating user $it", it)
                    cont.resume(
                        L15Result.Error(PLErrorCodes.ERROR_CREATING_USER),
                        onCancellation = { _, _, _ -> }
                    )
                }
        }

        emit(result)
    }

    override fun createGift(gift: PLGift): Flow<L15Result<Boolean, PLErrorCodes>> = flow {
        emit(L15Result.Loading)
        val result = suspendCancellableCoroutine { cont ->
            firestore.collection(PLFirebaseConst.USERS_NODE)
                .document(userDataProvider.pairCode.value)
                .collection(PLFirebaseConst.GIFTS_NODE)
                .document(gift.id)
                .set(gift)
                .addOnSuccessListener {
                    cont.resume(
                        L15Result.Success(true),
                        onCancellation = { _, _, _ -> }
                    )
                }
                .addOnFailureListener {
                    L15Logger.e(TAG, "createGift", "Error creating gift $it", it)
                    cont.resume(
                        L15Result.Error(PLErrorCodes.ERROR_CREATING_GIFT),
                        onCancellation = { _, _, _ -> }
                    )
                }
        }

        emit(result)
    }

    override fun listenToGifts(): Flow<List<PLGiftEntity>> = callbackFlow {
        val listener = firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.GIFTS_NODE)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    L15Logger.e(TAG, "listenToGifts", "Error listening to gifts $error")
                    close(error)
                    return@addSnapshotListener
                }

                val gifts = snapshot?.documents?.mapNotNull {
                    it.toObject(PLGiftEntity::class.java)
                }
                trySend(gifts ?: emptyList())
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun redeemGift(id: String) {
        firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.GIFTS_NODE)
            .document(id)
            .update(PLFirebaseConst.REDEEMED_FIELD, true)
            .await()
    }

    override suspend fun updateNotificationsToken(token: String) {
        val pairCode = withTimeoutOrNull(5000) {
            userDataProvider.pairCode.first { it.isNotBlank() }
        } ?: return

        val role = withTimeoutOrNull(5000) {
            userDataProvider.role.first { it.isNotBlank() }
        } ?: return

        firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(pairCode)
            .collection(PLFirebaseConst.ROLES_NODE)
            .document(role)
            .update(PLFirebaseConst.NOTIFICATIONS_TOKEN_FIELD, token)
            .await()
    }
}