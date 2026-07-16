package com.example.healthtracker.presentation.food_diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.data.local.db.entity.UserEntity
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.FoodDiaryRepository
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
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodDiaryUiState())
    val uiState: StateFlow<FoodDiaryUiState> = _uiState.asStateFlow()

    private val dbDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val displayDateFormat = SimpleDateFormat("dd 'Tháng' MM,yyyy", Locale("vi", "VN"))

    private var currentCalendar = Calendar.getInstance()
    private var mealCollectJob: Job? = null

    init {
        getUserTargetCalories()
        loadDiaryForDate(currentCalendar.time)
    }

    private fun getUserTargetCalories() {
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val user = userDao.getUserByEmail(email)
                if (user != null) {
                    val target = calculateCalories(user)
                    _uiState.update { it.copy(targetCalories = target) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

        mealCollectJob?.cancel()
        mealCollectJob = viewModelScope.launch {
            foodDiaryRepository.getMeal(dateString).collect { mealsList ->
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

    private fun calculateCalories(user: UserEntity): Int {
        val birthDate = user.birthDate ?: return 2100
        val genderStr = user.gender ?: "MALE"

        val genderEnum = if (genderStr == "MALE") Gender.MALE else Gender.FEMALE
        val activeLevelEnum = ActivityLevel.valueOf(user.activeLevel ?: "MODERATELY_ACTIVE")
        val goalTypeEnum = GoalType.valueOf(user.goalType ?: "MAINTAIN_WEIGHT")
        val profile = Profile(
            userName = user.userName ?: "",
            birthDate = birthDate,
            gender = genderEnum,
            weight = user.weight ?: 60f,
            height = user.height ?: 170f,
            activeLevel = activeLevelEnum,
            goalType = goalTypeEnum
        )
        return (profile.tdee + profile.goalType.caloriesOffset).toInt()
    }

    fun selectDateByMillis(millis: Long) {
        currentCalendar.timeInMillis = millis
        loadDiaryForDate(currentCalendar.time)
    }

    fun deleteMeal(meal: MealEntity) {
        viewModelScope.launch {
            foodDiaryRepository.deleteMeal(meal)
        }
    }
}
