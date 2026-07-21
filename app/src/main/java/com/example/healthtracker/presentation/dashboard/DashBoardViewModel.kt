package com.example.healthtracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.ActivityDiaryRepository
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import com.example.healthtracker.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val foodDiaryRepository: FoodDiaryRepository,
    private val activityDiaryRepository: ActivityDiaryRepository,
    private val userProfileRepository: UserProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        isLenient = false
    }
    private var dashboardCollectJob: Job? = null

    fun loadDashboard(date: Date = Date()) {
        val dateString = databaseDateFormat.format(date)
        _uiState.update {
            it.copy(
                selectDate = dateString,
                isLoading = true
            )
        }

        dashboardCollectJob?.cancel()
        dashboardCollectJob = viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = userProfileRepository.getProfile(email)
                val targetCalories = profile?.let(::calculateTargetCalories) ?: 0

                _uiState.update {
                    it.copy(
                        userName = profile?.userName.orEmpty(),
                        targetCalories = targetCalories
                    )
                }

                combine(
                    foodDiaryRepository.getMeal(dateString),
                    activityDiaryRepository.getActivitiesByDate(dateString)
                ) { meals, activities ->
                    meals to activities
                }.collect { (meals, activities) ->
                    val caloriesConsumed = meals.sumOf { it.calories }
                    val caloriesBurned = activities.sumOf { it.caloriesBurned }
                    val calorieBalance = caloriesConsumed - caloriesBurned
                    val remainingCalories = targetCalories - calorieBalance
                    val progressFloat = if (targetCalories > 0) {
                        (calorieBalance.coerceAtLeast(0).toFloat() / targetCalories)
                            .coerceIn(0f, 1f)
                    } else {
                        0f
                    }

                    _uiState.update {
                        it.copy(
                            meals = meals,
                            caloriesConsumed = caloriesConsumed,
                            caloriesBurned = caloriesBurned,
                            calorieBalance = calorieBalance,
                            remainingCalories = remainingCalories,
                            progressFloat = progressFloat,
                            isLoading = false
                        )
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun calculateTargetCalories(profile: Profile): Int {
        return profile.tdee.toInt().coerceAtLeast(0)
    }
}
