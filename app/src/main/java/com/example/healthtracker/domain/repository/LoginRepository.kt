package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.User

interface LoginRepository {
    suspend fun login(email: String, password: String): User
}