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
            it.copy(userName = value, errorResId = null)
        }
    }

    fun onBirthDateChange(value: String) {
        _uiState.update { state ->
            val age = value.toAge()
            state.copy(birthDate = value, age = age, errorResId = null)
        }
    }

    fun onGenderChange(value: Gender) {
        _uiState.update {
            it.copy(gender = value)
        }
    }

    fun onWeightChange(value: String) {
        _uiState.update {
            it.copy(weight = value, errorResId = null)
        }
    }

    fun onHeightChange(value: String) {
        _uiState.update {
            it.copy(height = value, errorResId = null)
        }
    }

    fun onActivityLevelChange(value: ActivityLevel) {
        _uiState.update {
            it.copy(activityLevel = value, errorResId = null)
        }
    }

    fun onGoalChange(value: GoalType) {
        _uiState.update {
            it.copy(goalType = value, errorResId = null)
        }
    }

    fun onSaveProfile() {
        viewModelScope.launch {
            val state = _uiState.value
            val activeLevel = state.activityLevel
            val goalType = state.goalType

            if (activeLevel == null) {
                _uiState.update {
                    it.copy(errorResId = R.string.error_activity_level_empty)
                }
                return@launch
            }

            if (goalType == null) {
                _uiState.update {
                    it.copy(errorResId = R.string.error_goal_empty)
                }
                return@launch
            }

            _uiState.update {
                it.copy(isLoading = true, errorResId = null)
            }
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = Profile(
                    userName = state.userName.trim(),
                    birthDate = state.birthDate.trim(),
                    gender = state.gender,
                    weight = state.weight.toFloatOrNull() ?: 0f,
                    height = state.height.toFloatOrNull() ?: 0f,
                    activeLevel = activeLevel,
                    goalType = goalType
                )
                setupProfileUseCase(email, profile)
                _uiState.update { it.copy(isLoading = false) }
                _navigationEvent.emit(SetupProfileNavigationEvent.ProfileSaved)
            } catch (e: Exception) {
                val errorResId = when (e.message) {
                    "ERR_NAME_EMPTY" -> R.string.error_name_empty
                    "ERR_BIRTHDAY_EMPTY" -> R.string.error_birthday_empty
                    "ERR_BIRTHDAY_INVALID" -> R.string.error_birthday_invalid
                    "ERR_WEIGHT_INVALID" -> R.string.error_weight_invalid
                    "ERR_HEIGHT_INVALID" -> R.string.error_height_invalid
                    else -> R.string.error_unknown
                }
                _uiState.update {
                    it.copy(isLoading = false, errorResId = errorResId)
                }
            }
        }
    }
}
