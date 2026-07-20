package com.example.healthtracker.presentation.activity_diary

import com.example.healthtracker.data.local.db.entity.UserActivityEntity

data class ActivityDiaryUiState (
    val selectDate: String = "",
    val activity: List<UserActivityEntity> = emptyList(),
    val totalCalories: Int = 0,
    val targetCalories: Int = 0,
    val progressPercentage: Int = 0,
    val progressFloat: Float = 0f,
    val isLoading: Boolean = false
)
