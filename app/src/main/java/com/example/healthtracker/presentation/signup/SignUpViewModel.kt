package com.example.healthtracker.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.R
import com.example.healthtracker.domain.usecase.signup.SignUpUseCase
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
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<SignUpNavigationEvent>()
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

    fun onConfirmPasswordChange(value: String) {
        _uiState.update {
            it.copy(confirmPassword = value, errorResId = null)
        }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    fun onSignUp() {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(isLoading = true, errorResId = null)
        }

        viewModelScope.launch {
            try {
                val state = _uiState.value

                signUpUseCase(
                    email = state.email,
                    password = state.password,
                    confirmPassword = state.confirmPassword
                )
                _uiState.update { it.copy(isLoading = false) }

                _navigationEvent.emit(SignUpNavigationEvent.NavigateToLogin)
            } catch (e: Exception) {
                val stringResId = when (e.message) {
                    "ERR_EMAIL_EMPTY" -> R.string.error_email_empty
                    "ERR_EMAIL_INVALID" -> R.string.error_email_invalid
                    "ERR_EMAIL_EXISTS" -> R.string.error_email_exists
                    "ERR_PASSWORD_EMPTY" -> R.string.error_password_empty
                    "ERR_PASSWORD_TOO_SHORT" -> R.string.error_password_too_short
                    "ERR_CONFIRM_PASSWORD_EMPTY" -> R.string.error_confirm_password_empty
                    "ERR_PASSWORDS_DO_NOT_MATCH" -> R.string.error_password_mismatch
                    else -> R.string.error_unknown
                }

                _uiState.update {
                    it.copy(isLoading = false, errorResId = stringResId)
                }
            }
        }
    }

    sealed interface SignUpNavigationEvent {
        data object NavigateToLogin : SignUpNavigationEvent
    }
}
