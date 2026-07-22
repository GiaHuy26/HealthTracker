package com.example.healthtracker.presentation.food_diary.add_food

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.data.repository.AppSettingsManager
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val foodDiaryRepository: FoodDiaryRepository,
    private val appSettingsManager: AppSettingsManager,
    private val sessionManager: SessionManager,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddFoodUiState())
    val uiState: StateFlow<AddFoodUiState> = _uiState.asStateFlow()

    private val _saveEvent = Channel<Unit>()
    val saveEvent = _saveEvent.receiveAsFlow()

    private var selectedDate: String = ""
    private var initialMealType: String? = null

    fun initData(date: String, mealTypeStr: String?) {
        this.selectedDate = date
        this.initialMealType = mealTypeStr
        val initialType = mealTypeStr?.let { typeStr ->
            MealType.entries.find { it.name.equals(typeStr, ignoreCase = true) }
        } ?: MealType.BREAKFAST

        _uiState.update { it.copy(selectedMealType = initialType) }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val email = sessionManager.getCurrentUserEmail()
            val languageCode = appSettingsManager.settings.first().language.code
            foodDiaryRepository.seedSampleFood(context, languageCode, email)
            loadAllFoods()
            if (mealTypeStr != null) {
                loadExistingMeals(email, date, mealTypeStr)
            } else {
                _uiState.update { it.copy(selectedFoods = emptyList()) }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun loadExistingMeals(
        userEmail: String,
        date: String,
        mealType: String
    ) {
        try {
            val existingMeals = foodDiaryRepository.getMealsByDateAndType(
                userEmail,
                date,
                mealType
            )
            if (existingMeals.isNotEmpty()) {
                val selectedItems = existingMeals.map { meal ->
                    val matchingFood = _uiState.value.listFoods.find { it.name.equals(meal.foodName, ignoreCase = true) }
                        ?: FoodEntity(
                            id = 0,
                            name = meal.foodName,
                            calories = if (meal.quantity > 0f) (meal.calories / meal.quantity).toInt() else meal.calories,
                            servingSize = meal.servingSize
                        )
                    SelectedFoodItem(
                        foods = matchingFood,
                        quantity = meal.quantity
                    )
                }
                _uiState.update { it.copy(selectedFoods = selectedItems) }
            } else {
                _uiState.update { it.copy(selectedFoods = emptyList()) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun loadAllFoods() {
        try {
            val foods = foodDiaryRepository.searchFoods("")
            _uiState.update { it.copy(listFoods = foods) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSelectedMeal(mealType: MealType) {
        _uiState.update { it.copy(selectedMealType = mealType) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            try {
                if (query.isEmpty()) {
                    _uiState.update { it.copy(searchResults = emptyList()) }
                } else {
                    val results = foodDiaryRepository.searchFoods(query)
                    _uiState.update { it.copy(searchResults = results) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onAddFoodClick(food: FoodEntity) {
        _uiState.update { state ->
            val existingIndex = state.selectedFoods.indexOfFirst { it.foods.id == food.id }
            val updatedList = state.selectedFoods.toMutableList()
            if (existingIndex != -1) {
                val existingItem = updatedList[existingIndex]
                updatedList[existingIndex] = existingItem.copy(quantity = existingItem.quantity + 1.0f)
            } else {
                updatedList.add(SelectedFoodItem(foods = food, quantity = 1.0f))
            }
            state.copy(selectedFoods = updatedList)
        }
    }

    fun onRemoveAllClick() {
        _uiState.update { it.copy(selectedFoods = emptyList()) }
    }

    fun onIncrementQuantity(item: SelectedFoodItem) {
        _uiState.update { state ->
            val index = state.selectedFoods.indexOfFirst { it.foods.id == item.foods.id }
            if (index != -1) {
                val updatedList = state.selectedFoods.toMutableList()
                val currentItem = updatedList[index]
                updatedList[index] = currentItem.copy(quantity = currentItem.quantity + 0.5f)
                state.copy(selectedFoods = updatedList)
            } else state
        }
    }

    fun onDecrementQuantity(item: SelectedFoodItem) {
        _uiState.update { state ->
            val index = state.selectedFoods.indexOfFirst { it.foods.id == item.foods.id }
            if (index != -1) {
                val updatedList = state.selectedFoods.toMutableList()
                val currentItem = updatedList[index]
                val newQuantity = currentItem.quantity - 0.5f
                if (newQuantity <= 0f) {
                    updatedList.removeAt(index)
                } else {
                    updatedList[index] = currentItem.copy(quantity = newQuantity)
                }
                state.copy(selectedFoods = updatedList)
            } else state
        }
    }

    fun onSavingMeal() {
        val selected = _uiState.value.selectedFoods
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val mealTypeStr = (_uiState.value.selectedMealType ?: MealType.BREAKFAST).name
                val previousMealType = initialMealType

                previousMealType?.let { mealType ->
                    foodDiaryRepository.deleteMealsByType(
                        email,
                        selectedDate,
                        mealType
                    )
                }
                if (previousMealType == null ||
                    !previousMealType.equals(mealTypeStr, ignoreCase = true)
                ) {
                    foodDiaryRepository.deleteMealsByType(email, selectedDate, mealTypeStr)
                }
                selected.forEach { item ->
                    val meal = MealEntity(
                        userEmail = email,
                        date = selectedDate,
                        mealType = mealTypeStr,
                        foodName = item.foods.name,
                        calories = (item.foods.calories * item.quantity).toInt(),
                        servingSize = item.foods.servingSize,
                        quantity = item.quantity
                    )
                    foodDiaryRepository.addMeal(meal)
                }
                _uiState.update { it.copy(isSaving = false) }
                _saveEvent.send(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
