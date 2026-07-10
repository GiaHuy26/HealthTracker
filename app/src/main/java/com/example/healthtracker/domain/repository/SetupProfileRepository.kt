package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.model.User

interface SetupProfileRepository {
    suspend fun saveProfile(email: String, profile: Profile)
}