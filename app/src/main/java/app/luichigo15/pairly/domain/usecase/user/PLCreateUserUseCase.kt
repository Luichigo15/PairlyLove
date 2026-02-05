package app.luichigo15.pairly.domain.usecase.user

import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.repository.PLUserRepository
import javax.inject.Inject

class PLCreateUserUseCase @Inject constructor(
    private val userRepository: PLUserRepository,
) {
    operator fun invoke(user: PLUser) = userRepository.createUser(user)
}