package com.example.healthtracker.presentation.activity_diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.ActivityDiaryRepository
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
class ActivityDiaryViewModel @Inject constructor(
    private val activityDiaryRepository: ActivityDiaryRepository,
    private val userProfileRepository: UserProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityDiaryUiState())
    val uiState: StateFlow<ActivityDiaryUiState> = _uiState.asStateFlow()

    private val dbDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        isLenient = false
    }

    private var selectedCalendar = Calendar.getInstance()
    private var activityCollectJob: Job? = null

    init {
        loadDiaryForDate(selectedCalendar.time)
    }

    private fun calculateTargetBurnedCalories(profile: Profile): Int? {
        val activeCalories = (profile.tdee - profile.bmr).toInt()
        return activeCalories.takeIf { it > 0 }
    }

    fun loadDiaryForDate(date: Date) {
        val dateString = dbDateFormat.format(date)
        _uiState.update {
            it.copy(
                selectDate = dateString,
                isLoading = true
            )
        }

        activityCollectJob?.cancel()
        activityCollectJob = viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = userProfileRepository.getProfile(email)
                val currentTarget = profile?.let {
                    calculateTargetBurnedCalories(it)
                } ?: 0

                activityDiaryRepository.getActivitiesByDate(email, dateString)
                    .collect { activities ->
                        val totalCalories = activities.sumOf { it.caloriesBurned }
                        val progressFloat = if (currentTarget > 0) {
                            totalCalories.toFloat() / currentTarget
                        } else {
                            0f
                        }
                        val progressPercentage = (progressFloat * 100)
                            .toInt()
                            .coerceAtMost(100)

                        _uiState.update {
                            it.copy(
                                activity = activities,
                                totalCalories = totalCalories,
                                targetCalories = currentTarget,
                                progressFloat = progressFloat,
                                progressPercentage = progressPercentage,
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun selectDateByMillis(millis: Long) {
        selectedCalendar.timeInMillis = millis
        loadDiaryForDate(selectedCalendar.time)
    }

    fun addActivity(type: ActivityType, durationMinutes: Int) {
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val weight = userProfileRepository.getWeight(email) ?: return@launch

                val calories = (type.met * weight * (durationMinutes / 60.0)).toInt()
                val entity = UserActivityEntity(
                    userEmail = email,
                    date = _uiState.value.selectDate,
                    activityType = type.name,
                    durationMinutes = durationMinutes,
                    caloriesBurned = calories
                )
                activityDiaryRepository.addActivity(entity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteActivity(entity: UserActivityEntity) {
        viewModelScope.launch {
            try {
                activityDiaryRepository.deleteActivity(entity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
