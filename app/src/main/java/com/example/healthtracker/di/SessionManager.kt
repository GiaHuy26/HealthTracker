package com.example.healthtracker.di

interface SessionManager {
    suspend fun saveUserEmail(email: String)
    suspend fun getCurrentUserEmail(): String
    suspend fun clearSession()
}