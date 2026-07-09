package com.example.healthtracker.presentation.signup

data class SignUpUiState(
    val userName: String="",
    val email: String="",
    val password: String="",
    val confirmPassword: String="",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)