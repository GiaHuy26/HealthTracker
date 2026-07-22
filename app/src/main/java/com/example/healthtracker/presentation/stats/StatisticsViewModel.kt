package com.example.healthtracker.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.StatisticsPeriod
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
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val foodDiaryRepository: FoodDiaryRepository,
    private val activityDiaryRepository: ActivityDiaryRepository,
    private val userProfileRepository: UserProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private var loadJob: Job? = null

    fun selectPeriod(period: StatisticsPeriod) {
        loadStatistics(period)
    }

    fun refreshStatistics() {
        loadStatistics(_uiState.value.selectedPeriod)
    }

    private fun loadStatistics(period: StatisticsPeriod) {
        val today = Calendar.getInstance()
        val firstDay = Calendar.getInstance()
        firstDay.add(Calendar.DAY_OF_YEAR, -(period.numberOfDays - 1))

        val startDate = dateFormat.format(firstDay.time)
        val endDate = dateFormat.format(today.time)

        _uiState.update {
            it.copy(
                selectedPeriod = period,
                isLoading = true
            )
        }

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = userProfileRepository.getProfile(email)
                val targetCalories = profile?.dailyCalorieTarget?.toInt() ?: 0

                val mealsFlow = foodDiaryRepository.getMealsBetweenDates(
                    email,
                    startDate,
                    endDate
                )
                val activitiesFlow = activityDiaryRepository.getActivitiesBetweenDates(
                    email,
                    startDate,
                    endDate
                )

                combine(mealsFlow, activitiesFlow) { meals, activities ->
                    meals to activities
                }.collect { data ->
                    val meals = data.first
                    val activities = data.second
                    val dailyList = mutableListOf<DailyCalories>()
                    val currentDate = firstDay.clone() as Calendar

                    repeat(period.numberOfDays) {
                        val date = dateFormat.format(currentDate.time)
                        val caloriesIn = meals
                            .filter { it.date == date }
                            .sumOf { it.calories }
                        val caloriesBurned = activities
                            .filter { it.date == date }
                            .sumOf { it.caloriesBurned }

                        val calorieBalance = caloriesIn - caloriesBurned
                        val minTarget = (targetCalories * 0.9).toInt()
                        val maxTarget = (targetCalories * 1.1).toInt()
                        val reachedTarget = caloriesIn > 0 &&
                            targetCalories > 0 &&
                            calorieBalance in minTarget..maxTarget

                        dailyList.add(
                            DailyCalories(
                                date = date,
                                caloriesIn = caloriesIn,
                                caloriesBurned = caloriesBurned,
                                reachedTarget = reachedTarget
                            )
                        )

                        currentDate.add(Calendar.DAY_OF_YEAR, 1)
                    }

                    updateStatistics(dailyList, targetCalories)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateStatistics(
        dailyList: List<DailyCalories>,
        targetCalories: Int
    ) {
        var totalCaloriesIn = 0
        var totalCaloriesBurned = 0
        var targetDays = 0

        dailyList.forEach { day ->
            totalCaloriesIn += day.caloriesIn
            totalCaloriesBurned += day.caloriesBurned
            if (day.reachedTarget) targetDays++
        }

        var currentStreak = 0
        for (index in dailyList.indices.reversed()) {
            if (dailyList[index].reachedTarget) {
                currentStreak++
            } else {
                break
            }
        }

        val numberOfDays = dailyList.size
        val averageCaloriesIn = if (numberOfDays > 0) {
            totalCaloriesIn / numberOfDays
        } else {
            0
        }
        val averageCaloriesBurned = if (numberOfDays > 0) {
            totalCaloriesBurned / numberOfDays
        } else {
            0
        }
        val targetProgress = if (numberOfDays > 0) {
            targetDays.toFloat() / numberOfDays
        } else {
            0f
        }

        _uiState.update {
            it.copy(
                dailyCalories = dailyList,
                targetCalories = targetCalories,
                averageCaloriesIn = averageCaloriesIn,
                averageCaloriesBurned = averageCaloriesBurned,
                targetDays = targetDays,
                currentStreak = currentStreak,
                targetProgress = targetProgress,
                isLoading = false
            )
        }
    }
}
