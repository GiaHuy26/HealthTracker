package com.example.healthtracker.data.repository

import android.content.Context
import androidx.core.content.edit
import com.example.healthtracker.domain.model.AppColor
import com.example.healthtracker.domain.model.AppFontSize
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = context.getSharedPreferences(
        "app_settings",
        Context.MODE_PRIVATE
    )

    private val _settings = MutableStateFlow(readSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    fun setTheme(theme: AppTheme) {
        preferences.edit { putString("theme", theme.name) }
        _settings.value = _settings.value.copy(theme = theme)
    }

    fun setColor(color: AppColor) {
        preferences.edit { putString("color", color.name) }
        _settings.value = _settings.value.copy(color = color)
    }

    fun setFontSize(fontSize: AppFontSize) {
        preferences.edit { putString("font_size", fontSize.name) }
        _settings.value = _settings.value.copy(fontSize = fontSize)
    }

    fun setLanguage(language: AppLanguage) {
        preferences.edit { putString("language", language.name) }
        _settings.value = _settings.value.copy(language = language)
    }

    private fun readSettings(): AppSettings {
        val savedTheme = preferences.getString("theme", null)
        val savedColor = preferences.getString("color", null)
        val savedFontSize = preferences.getString("font_size", null)
        val savedLanguage = preferences.getString("language", null)

        return AppSettings(
            theme = AppTheme.entries.find { it.name == savedTheme } ?: AppTheme.LIGHT,
            color = AppColor.entries.find { it.name == savedColor } ?: AppColor.GREEN,
            fontSize = AppFontSize.entries.find {
                it.name == savedFontSize
            } ?: AppFontSize.MEDIUM,
            language = AppLanguage.entries.find {
                it.name == savedLanguage
            } ?: AppLanguage.VIETNAMESE
        )
    }
}
