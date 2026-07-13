package com.example.healthtracker.domain.model

data class User(
    val id: Int,
    val email: String,
    val isProfileCompleted: Boolean = false
)