package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.User

interface SignUpRepository {
    suspend fun signUp( email: String, password: String): User
}