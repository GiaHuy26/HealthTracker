package com.example.healthtracker.data.repository

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.healthtracker.domain.model.AppColor
import com.example.healthtracker.domain.model.AppFontSize
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.appSettingsDataStore by preferencesDataStore(name = "app_settings")

@Singleton
class AppSettingsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.appSettingsDataStore

    val settings: Flow<AppSettings> = dataStore.data.map { preferences ->
        AppSettings(
            theme = AppTheme.entries.find {
                it.name == preferences[themeKey]
            } ?: AppTheme.LIGHT,
            color = AppColor.entries.find {
                it.name == preferences[colorKey]
            } ?: AppColor.GREEN,
            fontSize = AppFontSize.entries.find {
                it.name == preferences[fontSizeKey]
            } ?: AppFontSize.MEDIUM,
            language = AppLanguage.entries.find {
                it.name == preferences[languageKey]
            } ?: AppLanguage.VIETNAMESE
        )
    }

    suspend fun setTheme(theme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }

    suspend fun setColor(color: AppColor) {
        dataStore.edit { preferences ->
            preferences[colorKey] = color.name
        }
    }

    suspend fun setFontSize(fontSize: AppFontSize) {
        dataStore.edit { preferences ->
            preferences[fontSizeKey] = fontSize.name
        }
    }

    suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[languageKey] = language.name
        }
    }

    private companion object {
        val themeKey = stringPreferencesKey("theme")
        val colorKey = stringPreferencesKey("color")
        val fontSizeKey = stringPreferencesKey("font_size")
        val languageKey = stringPreferencesKey("language")
    }
}
