package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.Profile

interface UserProfileRepository {
    suspend fun getProfile(email: String): Profile?
    suspend fun getWeight(email: String): Float?
}
