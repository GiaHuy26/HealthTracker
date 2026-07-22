package com.example.healthtracker.domain.usecase.signup

import com.example.healthtracker.domain.model.User
import com.example.healthtracker.domain.repository.SignUpRepository
import com.example.healthtracker.domain.validation.isValidEmail
import com.example.healthtracker.domain.validation.normalizeEmail
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
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
        if (confirmPassword.isBlank()) {
            throw Exception("ERR_CONFIRM_PASSWORD_EMPTY")
        }
        if (password != confirmPassword) {
            throw Exception("ERR_PASSWORDS_DO_NOT_MATCH")
        }

        return signUpRepository.signUp(normalizedEmail, password)
    }
}
