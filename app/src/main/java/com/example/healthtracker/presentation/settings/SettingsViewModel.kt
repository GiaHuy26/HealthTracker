package com.example.healthtracker.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.repository.AppSettingsManager
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.AppColor
import com.example.healthtracker.domain.model.AppFontSize
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.AppTheme
import com.example.healthtracker.domain.repository.UserProfileRepository
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val sessionManager: SessionManager,
    private val appSettingsManager: AppSettingsManager,
    private val foodDiaryRepository: FoodDiaryRepository,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    val appSettings: StateFlow<AppSettings> = appSettingsManager.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AppSettings()
    )

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val email = sessionManager.getCurrentUserEmail()
                val profile = userProfileRepository.getProfile(email)
                _uiState.update {
                    it.copy(profile = profile, isLoading = false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            appSettingsManager.setTheme(theme)
        }
    }

    fun setColor(color: AppColor) {
        viewModelScope.launch {
            appSettingsManager.setColor(color)
        }
    }

    fun setFontSize(fontSize: AppFontSize) {
        viewModelScope.launch {
            appSettingsManager.setFontSize(fontSize)
        }
    }

    fun setLanguage(
        language: AppLanguage,
        onLanguageChanged: () -> Unit
    ) {
        viewModelScope.launch {
            appSettingsManager.setLanguage(language)
            val email = sessionManager.getCurrentUserEmail()
            foodDiaryRepository.seedSampleFood(context, language.code, email)
            onLanguageChanged()
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            sessionManager.clearSession()
            onLogoutComplete()
        }
    }
}
