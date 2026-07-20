package com.example.healthtracker.domain.model

import com.example.healthtracker.R

enum class MealType(val titleResId: Int) {
    BREAKFAST(R.string.food_diary_meal_breakfast),
    LUNCH(R.string.food_diary_meal_lunch),
    DINNER(R.string.food_diary_meal_dinner),
    SNACK(R.string.food_diary_meal_snack)
}