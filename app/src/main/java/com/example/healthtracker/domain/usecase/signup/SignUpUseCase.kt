package com.example.healthtracker.domain.usecase.signup

import android.util.Patterns
import com.example.healthtracker.domain.model.User
import com.example.healthtracker.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): User {
        if (email.isBlank()) {
            throw Exception("ERR_EMAIL_EMPTY")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw Exception("ERR_EMAIL_INVALID")
        }
        if (password.isBlank()) {
            throw Exception("ERR_PASSWORD_EMPTY")
        }
        if (password.length < 6) {
            throw Exception("ERR_PASSWORD_TOO_SHORT")
        }
        if (password != confirmPassword) {
            throw Exception("ERR_PASSWORDS_DO_NOT_MATCH")
        }

        return signUpRepository.signUp(email, password)
    }
}