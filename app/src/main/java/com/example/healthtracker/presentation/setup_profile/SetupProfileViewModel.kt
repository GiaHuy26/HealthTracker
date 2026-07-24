package com.example.healthtracker.presentation.setup_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.R
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.model.toAge
import com.example.healthtracker.domain.repository.UserProfileRepository
import com.example.healthtracker.domain.usecase.setup_profile.SetupProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupProfileViewModel @Inject constructor(
    private val setupProfileUseCase: SetupProfileUseCase,
    private val sessionManager: SessionManager,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SetupProfileUiState())
    val uiState: StateFlow<SetupProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SetupProfileNavigationEvent>()
    val navigationEvent: SharedFlow<SetupProfileNavigationEvent> = _navigationEvent.asSharedFlow()

    sealed interface SetupProfileNavigationEvent {
        data object ProfileSaved : SetupProfileNavigationEvent
    }

    fun loadCurrentProfile() {
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = userProfileRepository.getProfile(email) ?: return@launch

                _uiState.update {
                    it.copy(
                        userName = profile.userName,
                        birthDate = profile.birthDate,
                        age = profile.age,
                        gender = profile.gender,
                        weight = profile.weight.toString(),
                        height = profile.height.toString(),
                        activityLevel = profile.activeLevel,
                        goalType = profile.goalType
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onUserNameChange(value: String) {
        _uiState.update {
            it.copy(userName = value, userNameErrorResId = null)
        }
    }

    fun onBirthDateChange(value: String) {
        _uiState.update { state ->
            val age = value.toAge()
            state.copy(
                birthDate = value,
                age = age,
                birthDateErrorResId = null
            )
        }
    }

    fun onGenderChange(value: Gender) {
        _uiState.update {
            it.copy(gender = value)
        }
    }

    fun onWeightChange(value: String) {
        _uiState.update {
            it.copy(weight = value, weightErrorResId = null)
        }
    }

    fun onHeightChange(value: String) {
        _uiState.update {
            it.copy(height = value, heightErrorResId = null)
        }
    }

    fun onActivityLevelChange(value: ActivityLevel) {
        _uiState.update {
            it.copy(
                activityLevel = value,
                activityLevelErrorResId = null
            )
        }
    }

    fun onGoalChange(value: GoalType) {
        _uiState.update {
            it.copy(goalType = value, goalTypeErrorResId = null)
        }
    }

    fun onSaveProfile() {
        val profile = validateProfile() ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val email = sessionManager.getCurrentUserEmail()
                setupProfileUseCase(email, profile)
                _uiState.update { it.copy(isLoading = false) }
                _navigationEvent.emit(SetupProfileNavigationEvent.ProfileSaved)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalErrorResId = R.string.error_unknown
                    )
                }
            }
        }
    }

    private fun validateProfile(): Profile? {
        val state = _uiState.value
        val userName = state.userName.trim()
        val birthDate = state.birthDate.trim()
        val weight = state.weight.trim().toFloatOrNull()
        val height = state.height.trim().toFloatOrNull()
        val activityLevel = state.activityLevel
        val goalType = state.goalType

        val userNameError =
            if (userName.isBlank()) R.string.error_name_empty else null
        val birthDateError = when {
            birthDate.isBlank() -> R.string.error_birthday_empty
            birthDate.toAge() == null -> R.string.error_birthday_invalid
            else -> null
        }
        val weightError =
            if (weight == null || weight !in 20f..300f) R.string.error_weight_invalid else null
        val heightError =
            if (height == null || height !in 80f..250f) R.string.error_height_invalid else null
        val activityLevelError =
            if (activityLevel == null) R.string.error_activity_level_empty else null
        val goalTypeError =
            if (goalType == null) R.string.error_goal_empty else null

        _uiState.update {
            it.copy(
                userNameErrorResId = userNameError,
                birthDateErrorResId = birthDateError,
                weightErrorResId = weightError,
                heightErrorResId = heightError,
                activityLevelErrorResId = activityLevelError,
                goalTypeErrorResId = goalTypeError,
                generalErrorResId = null
            )
        }

        if (weight == null ||
            height == null ||
            activityLevel == null ||
            goalType == null
        ) {
            return null
        }

        if (userNameError != null ||
            birthDateError != null ||
            weightError != null ||
            heightError != null
        ) {
            return null
        }

        return Profile(
            userName = userName,
            birthDate = birthDate,
            gender = state.gender,
            weight = weight,
            height = height,
            activeLevel = activityLevel,
            goalType = goalType
        )
    }
}
