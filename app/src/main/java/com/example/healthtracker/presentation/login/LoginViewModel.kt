package com.example.healthtracker.presentation.login

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.R
import com.example.healthtracker.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _navigationEvent = Channel<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

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
                _uiState.update { it.copy(isLoading = false) }
                _navigationEvent.send(LoginNavigationEvent.NavigationToHome)
            } catch (e: Exception) {
                val stringResId = when (e.message) {
                    "ERR_EMAIL_EMPTY" -> R.string.error_email_empty
                    "ERR_EMAIL_INVALID" -> R.string.error_email_exists
                    "ERR_PASSWORD_EMPTY" -> R.string.error_password_empty
                    "ERR_PASSWORD_TOO_SHORT" -> R.string.error_password_too_short
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
    }
}