package app.luichigo15.pairly.domain.usecase.user

import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.repository.PLUserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PLCreateUserUseCase @Inject constructor(
    private val userRepository: PLUserRepository,
) {
    operator fun invoke(user: PLUser) = flow {
        emit(L15Result.Loading)
        emit(userRepository.createUser(user))
    }
}