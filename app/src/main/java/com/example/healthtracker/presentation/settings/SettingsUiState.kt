package com.example.healthtracker.presentation.settings

import com.example.healthtracker.domain.model.Profile

data class SettingsUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = true
)
