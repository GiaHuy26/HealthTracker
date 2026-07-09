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
            throw Exception("")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw Exception("")
        }
        if (password.isBlank()) {
            throw Exception("")
        }
        if (password.length < 6) {
            throw Exception("")
        }
        if (password != confirmPassword) {
            throw Exception("")
        }

        return signUpRepository.signUp(email, password)
    }
}