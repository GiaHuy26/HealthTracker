package com.example.healthtracker.presentation.setup_profile

import androidx.lifecycle.ViewModel
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SetupProfileViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SetupProfileUiState())
    val uiState: StateFlow<SetupProfileUiState> = _uiState.asStateFlow()

    fun onUserNameChange(value: String) {
        _uiState.update {
            it.copy(userName = value, errorResId = null)
        }
    }

    fun onBirthDateChange(value: String) {
        _uiState.update {
            it.copy(birthDate = value, errorResId = null)
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
            it.copy(activityLevel = value)
        }
    }

    fun onGoalChange(value: GoalType) {
        _uiState.update {
            it.copy(goalType = value)
        }
    }
}