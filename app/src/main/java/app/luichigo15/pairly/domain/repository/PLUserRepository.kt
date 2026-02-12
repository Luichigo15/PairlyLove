package app.luichigo15.pairly.domain.repository

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.model.PLUser

interface PLUserRepository {
    suspend fun createUser(user: PLUser): L15Result<Boolean, PLErrorCodes>
}