package com.example.healthtracker.presentation.activity_diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.local.db.dao.UserActivityDao
import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import com.example.healthtracker.data.local.db.entity.UserEntity
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.domain.model.Profile
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
    private val userActivityDao: UserActivityDao,
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityDiaryUiState())
    val uiState: StateFlow<ActivityDiaryUiState> = _uiState.asStateFlow()

    private val dbDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val displayDateFormat = SimpleDateFormat("dd 'Tháng' MM,yyyy", Locale.forLanguageTag("vi-VN"))

    private var currentCalendar = Calendar.getInstance()
    private var activityCollectJob: Job? = null

    init {
        loadDiaryForDate(currentCalendar.time)
    }

    private fun calculateTargetBurnedCalories(user: UserEntity): Int {
        val birthDate = user.birthDate ?: return DEFAULT_TARGET_CALORIES
        val genderStr = user.gender ?: "MALE"
        val genderEnum = if (genderStr == "MALE") Gender.MALE else Gender.FEMALE
        val activeLevelEnum = ActivityLevel.valueOf(user.activeLevel ?: "MODERATELY_ACTIVE")
        val goalTypeEnum = GoalType.valueOf(user.goalType ?: "MAINTAIN_WEIGHT")
        
        val profile = Profile(
            userName = user.userName ?: "",
            birthDate = birthDate,
            gender = genderEnum,
            weight = user.weight ?: DEFAULT_WEIGHT,
            height = user.height ?: 170f,
            activeLevel = activeLevelEnum,
            goalType = goalTypeEnum
        )
        val activeCalories = (profile.tdee - profile.bmr).toInt()
        return if (activeCalories > 0) activeCalories else DEFAULT_TARGET_CALORIES
    }

    fun loadDiaryForDate(date: Date) {
        val dateString = dbDateFormat.format(date)
        val displayDateString = displayDateFormat.format(date)
        _uiState.update {
            it.copy(
                selectDate = dateString,
                displayDate = displayDateString,
                isLoading = true
            )
        }

        activityCollectJob?.cancel()
        activityCollectJob = viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val user = userDao.getUserByEmail(email)
                val currentTarget = if (user != null) calculateTargetBurnedCalories(user) else DEFAULT_TARGET_CALORIES

                userActivityDao.getActivitiesByDate(dateString).collect { activities ->
                    val totalCalories = activities.sumOf { it.caloriesBurned }
                    val progressFloat = if (currentTarget > 0) (totalCalories.toFloat() / currentTarget) else 0f
                    val progressPercentage = (progressFloat * 100).toInt().coerceAtMost(100)

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
        currentCalendar.timeInMillis = millis
        loadDiaryForDate(currentCalendar.time)
    }

    fun addActivity(type: ActivityType, durationMinutes: Int) {
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val user = userDao.getUserByEmail(email)
                val weight = user?.weight ?: DEFAULT_WEIGHT

                val calories = (type.met * weight * (durationMinutes / 60.0)).toInt()
                val entity = UserActivityEntity(
                    date = _uiState.value.selectDate,
                    activityType = type.name,
                    durationMinutes = durationMinutes,
                    caloriesBurned = calories
                )
                userActivityDao.insertActivity(entity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteActivity(entity: UserActivityEntity) {
        viewModelScope.launch {
            try {
                userActivityDao.deleteActivity(entity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val DEFAULT_WEIGHT = 60f
        private const val DEFAULT_TARGET_CALORIES = 500
    }
}
