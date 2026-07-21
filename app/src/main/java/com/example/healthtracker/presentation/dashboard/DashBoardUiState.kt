package com.example.healthtracker.presentation.dashboard

import com.example.healthtracker.data.local.db.entity.MealEntity

data class DashboardUiState(
    val selectDate: String = "",
    val userName: String = "",
    val meals: List<MealEntity> = emptyList(),
    val caloriesConsumed: Int = 0,
    val caloriesBurned: Int = 0,
    val targetCalories: Int = 0,
    val calorieBalance: Int = 0,
    val remainingCalories: Int = 0,
    val progressFloat: Float = 0f,
    val isLoading: Boolean = true
)
