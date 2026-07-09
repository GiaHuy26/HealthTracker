package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.User

interface SignUpRepository {
    suspend fun signUp(userName: String, email: String, password: String): Result<User>
}