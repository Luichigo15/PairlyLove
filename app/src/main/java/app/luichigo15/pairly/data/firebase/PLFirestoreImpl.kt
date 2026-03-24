package app.luichigo15.pairly.data.firebase

import app.luichigo15.common.utils.L15Logger
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.common.PLFirebaseConst
import app.luichigo15.pairly.common.PLRoleConst
import app.luichigo15.pairly.data.database.model.PLGiftEntity
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.model.PLPuzzle
import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.provider.PLUserDataProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
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

    override suspend fun createUser(user: PLUser): L15Result<Boolean, PLErrorCodes> {
        if (user.role == PLRoleConst.GIRL_ROLE && !checkUuidValid(user.uuid)) {
            return L15Result.Error(PLErrorCodes.UUID_DOES_NOT_EXIST)
        }

        return try {
            firestore.collection(PLFirebaseConst.USERS_NODE)
                .document(user.uuid)
                .collection(PLFirebaseConst.ROLES_NODE)
                .document(user.role)
                .set(user).await()

            L15Result.Success(true)
        } catch (e: Exception) {
            L15Logger.e(TAG, "createUser", "Error creating user $e", e)
            L15Result.Error(PLErrorCodes.ERROR_CREATING_USER)
        }
    }

    override suspend fun createGift(gift: PLGift): L15Result<Boolean, PLErrorCodes> {
        return try {
            firestore.collection(PLFirebaseConst.USERS_NODE)
                .document(userDataProvider.pairCode.value)
                .collection(PLFirebaseConst.GIFTS_NODE)
                .document(gift.id)
                .set(gift)
                .await()

            L15Result.Success(true)
        } catch (e: Exception) {
            L15Logger.e(TAG, "createGift", "Error creating gift $e", e)
            return L15Result.Error(PLErrorCodes.ERROR_CREATING_GIFT)
        }
    }

    override fun observeGifts(): Flow<List<PLGiftEntity>> = callbackFlow {
        val listener = firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.GIFTS_NODE)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    L15Logger.e(TAG, "observeGifts", "Error listening to gifts $error")
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

    override suspend fun redeemGift(id: String) = try {
        firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.GIFTS_NODE)
            .document(id)
            .update(PLFirebaseConst.REDEEMED_FIELD, true)
            .await()
        true
    } catch (e: Exception) {
        L15Logger.e(TAG, "redeemGift", "Error redeeming gift $e", e)
        false
    }

    override suspend fun updateNotificationsToken(token: String) {
        val pairCode = withTimeoutOrNull(5000) {
            userDataProvider.pairCode.first { it.isNotBlank() }
        } ?: return

        val role = withTimeoutOrNull(5000) {
            userDataProvider.role.first { it.isNotBlank() }
        } ?: return

        try {
            firestore.collection(PLFirebaseConst.USERS_NODE)
                .document(pairCode)
                .collection(PLFirebaseConst.ROLES_NODE)
                .document(role)
                .update(PLFirebaseConst.NOTIFICATIONS_TOKEN_FIELD, token)
                .await()
        } catch (e: Exception) {
            L15Logger.e(TAG, "updateNotificationsToken", "Error updating notifications token $e")
        }
    }

    override suspend fun createPuzzle(puzzle: PLPuzzle): Boolean = try {
        firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.PUZZLES_NODE)
            .document(puzzle.id)
            .set(puzzle)
            .await()
        true
    } catch (e: Exception) {
        L15Logger.e(TAG, "createPuzzle", "Error creating puzzle $e", e)
        false
    }

    override fun observePuzzles(): Flow<List<PLPuzzle>> = callbackFlow {
        val listener = firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.PUZZLES_NODE)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    L15Logger.e(TAG, "observePuzzles", "Error listening to puzzles $error")
                    close(error)
                    return@addSnapshotListener
                }

                val puzzles = snapshot?.documents?.mapNotNull {
                    it.toObject(PLPuzzle::class.java)
                }
                trySend(puzzles ?: emptyList())
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun deletePuzzle(id: String): Boolean = try {
        firestore.collection(PLFirebaseConst.USERS_NODE)
            .document(userDataProvider.pairCode.value)
            .collection(PLFirebaseConst.PUZZLES_NODE)
            .document(id)
            .delete()
            .await()
        true
    } catch (e: Exception) {
        L15Logger.e(TAG, "deletePuzzle", "Error deleting puzzle $e", e)
        false
    }
}