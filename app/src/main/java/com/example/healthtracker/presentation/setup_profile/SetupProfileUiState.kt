package com.example.healthtracker.presentation.setup_profile

import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType

data class SetupProfileUiState(
    val userName: String = "",
    val birthDate: String = "",
    val age: Int? = null,
    val gender: Gender = Gender.MALE,
    val weight: String = "",
    val height: String = "",
    val activityLevel: ActivityLevel? = null,
    val goalType: GoalType? = null,
    val isLoading: Boolean = false,
    val errorResId: Int? = null
)