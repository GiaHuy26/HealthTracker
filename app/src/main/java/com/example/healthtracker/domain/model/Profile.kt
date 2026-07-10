package com.example.healthtracker.domain.model

data class Profile(
    val userName: String,
    val birthDate: String,
    val gender: Gender,
    val weight: Float,
    val height: Float,
    val activeLevel: ActivityLevel,
    val goalType: GoalType
)