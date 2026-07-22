package com.example.healthtracker.presentation.food_diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import com.example.healthtracker.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FoodDiaryViewModel @Inject constructor(
    private val foodDiaryRepository: FoodDiaryRepository,
    private val sessionManager: SessionManager,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodDiaryUiState())
    val uiState: StateFlow<FoodDiaryUiState> = _uiState.asStateFlow()

    private val dbDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        isLenient = false
    }

    private var selectedCalendar = Calendar.getInstance()
    private var mealCollectJob: Job? = null

    init {
        getUserTargetCalories()
        loadDiaryForDate(selectedCalendar.time)
    }

    private fun getUserTargetCalories() {
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = userProfileRepository.getProfile(email)
                val targetCalories = profile?.let { calculateCalories(it) } ?: 0
                _uiState.update { it.copy(targetCalories = targetCalories) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadDiaryForDate(date: Date) {
        val dateString = dbDateFormat.format(date)
        _uiState.update {
            it.copy(
                selectDate = dateString,
                isLoading = true
            )
        }

        mealCollectJob?.cancel()
        mealCollectJob = viewModelScope.launch {
            val email = sessionManager.getCurrentUserEmail()
            foodDiaryRepository.getMeal(email, dateString).collect { mealsList ->
                val targetCalories = _uiState.value.targetCalories
                val totalCalories = mealsList.sumOf { it.calories }

                val progressFloat =
                    if (targetCalories > 0) (totalCalories.toFloat() / targetCalories) else 0f
                val progressPercentage = (progressFloat * 100).toInt()

                _uiState.update {
                    it.copy(
                        meal = mealsList,
                        totalCalories = totalCalories,
                        progressPercentage = progressPercentage,
                        progressFloat = progressFloat.coerceIn(0f, 1f),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun calculateCalories(profile: Profile): Int? {
        return profile.dailyCalorieTarget.toInt().takeIf { it > 0 }
    }

    fun selectDateByMillis(millis: Long) {
        selectedCalendar.timeInMillis = millis
        loadDiaryForDate(selectedCalendar.time)
    }

    fun deleteMeal(meal: MealEntity) {
        viewModelScope.launch {
            foodDiaryRepository.deleteMeal(meal)
        }
    }
}
