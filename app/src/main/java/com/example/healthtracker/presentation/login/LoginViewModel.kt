package com.example.healthtracker.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.R
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(email = value, errorResId = null)
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value, errorResId = null)
        }
    }

    fun onPasswordVisible() {
        _uiState.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorResId = null)
            }
            try {
                val state = _uiState.value
                val user = loginUseCase(
                    email = state.email.trim(),
                    password = state.password
                )
                sessionManager.saveUserEmail(user.email)
                _uiState.update { it.copy(isLoading = false) }
                if (user.isProfileCompleted) {
                    _navigationEvent.emit(LoginNavigationEvent.NavigationToHome)
                } else {
                    _navigationEvent.emit(LoginNavigationEvent.NavigationToSetupProfile)
                }
            } catch (e: Exception) {
                val stringResId = when (e.message) {
                    "ERR_EMAIL_EMPTY" -> R.string.error_email_empty
                    "ERR_EMAIL_INVALID" -> R.string.error_email_invalid
                    "ERR_PASSWORD_EMPTY" -> R.string.error_password_empty
                    "ERR_PASSWORD_TOO_SHORT" -> R.string.error_password_too_short
                    "ERR_WRONG_CREDENTIALS" -> R.string.error_wrong_credentials
                    else -> R.string.error_unknown
                }
                _uiState.update {
                    it.copy(isLoading = false, errorResId = stringResId)
                }
            }
        }
    }

    sealed interface LoginNavigationEvent {
        data object NavigationToHome : LoginNavigationEvent
        data object NavigationToSetupProfile : LoginNavigationEvent
    }
}