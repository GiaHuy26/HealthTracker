package com.example.healthtracker.presentation.food_diary.add_food

import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.domain.model.MealType

data class SelectedFoodItem(
    val foods: FoodEntity,
    val quantity: Float = 1.0f
)

data class AddFoodUiState(
    val searchQuery: String = "",
    val selectedMealType: MealType? = null,
    val listFoods: List<FoodEntity> = emptyList(),
    val searchResults: List<FoodEntity> = emptyList(),
    val selectedFoods: List<SelectedFoodItem> = emptyList(),
    val note: String = "",
    val maxNoteLength: Int = 200,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaveSuccess: Boolean = false,
)