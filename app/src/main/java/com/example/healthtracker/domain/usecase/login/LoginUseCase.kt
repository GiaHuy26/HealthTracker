package com.example.healthtracker.domain.usecase.login

import android.util.Patterns
import com.example.healthtracker.domain.model.User
import com.example.healthtracker.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
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
        return loginRepository.login(email, password)
    }
}