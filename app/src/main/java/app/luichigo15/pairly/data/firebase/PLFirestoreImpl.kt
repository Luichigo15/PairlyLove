package app.luichigo15.pairly.data.firebase

import app.luichigo15.common.utils.L15Logger
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.common.PLFirebaseConst
import app.luichigo15.pairly.common.PLRoleConst
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.provider.PLPairCodeProvider
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PLFirestoreImpl @Inject constructor(
    private val pairCodeProvider: PLPairCodeProvider
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
                .document(pairCodeProvider.pairCode.value)
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
}