package com.example.healthtracker.domain.model

import com.example.healthtracker.R

enum class MealType(val mealType: String, val titleResId: Int) {
    BREAKFAST("Breakfast",R.string.food_diary_meal_breakfast),
    LUNCH("Lunch",R.string.food_diary_meal_lunch),
    DINNER("Dinner",R.string.food_diary_meal_dinner),
    SNACK("Snack",R.string.food_diary_meal_snack)
}