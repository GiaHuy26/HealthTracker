package com.example.healthtracker.domain.usecase.login

import com.example.healthtracker.domain.model.User
import com.example.healthtracker.domain.repository.LoginRepository
import com.example.healthtracker.domain.validation.isValidEmail
import com.example.healthtracker.domain.validation.normalizeEmail
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): User {
        val normalizedEmail = normalizeEmail(email)

        if (normalizedEmail.isBlank()) {
            throw Exception("ERR_EMAIL_EMPTY")
        }
        if (!isValidEmail(normalizedEmail)) {
            throw Exception("ERR_EMAIL_INVALID")
        }
        if (password.isBlank()) {
            throw Exception("ERR_PASSWORD_EMPTY")
        }
        if (password.length < 6) {
            throw Exception("ERR_PASSWORD_TOO_SHORT")
        }
        return loginRepository.login(normalizedEmail, password)
    }
}
