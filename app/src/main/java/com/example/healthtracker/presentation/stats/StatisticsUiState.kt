package com.example.healthtracker.presentation.stats

import com.example.healthtracker.domain.model.StatisticsPeriod

data class DailyCalories(
    val date: String,
    val caloriesIn: Int = 0,
    val caloriesBurned: Int = 0,
    val reachedTarget: Boolean = false
)

data class StatisticsUiState(
    val selectedPeriod: StatisticsPeriod = StatisticsPeriod.WEEK,
    val dailyCalories: List<DailyCalories> = emptyList(),
    val targetCalories: Int = 0,
    val averageCaloriesIn: Int = 0,
    val averageCaloriesBurned: Int = 0,
    val targetDays: Int = 0,
    val currentStreak: Int = 0,
    val targetProgress: Float = 0f,
    val isLoading: Boolean = true
)
