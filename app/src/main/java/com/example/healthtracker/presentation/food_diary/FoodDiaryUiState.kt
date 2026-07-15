package com.example.healthtracker.presentation.food_diary

import com.example.healthtracker.data.local.db.entity.MealEntity

data class FoodDiaryUiState(
    val selectDate: String = "",
    val displayDate: String = "",
    val meal: List<MealEntity> = emptyList(),
    val totalCalories: Int = 0,
    val targetCalories: Int = 0,
    val progressPercentage: Int = 0,
    val progressFloat: Float = 0f,
    val isLoading: Boolean = false
)