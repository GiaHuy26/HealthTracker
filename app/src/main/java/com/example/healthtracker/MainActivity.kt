package com.example.healthtracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.data.local.preferences.AppSettingsPreferences
import com.example.healthtracker.di.SessionManager
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.repository.UserProfileRepository
import com.example.healthtracker.navigation.AppNavKey
import com.example.healthtracker.navigation.AppNavigation
import com.example.healthtracker.navigation.HomeRoute
import com.example.healthtracker.navigation.NavigationManager
import com.example.healthtracker.navigation.SetupProfileRoute
import com.example.healthtracker.navigation.StartRoute
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var appSettingsPreferences: AppSettingsPreferences

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var userProfileRepository: UserProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            val initialSettings = appSettingsPreferences.settings.first()
            val initialRoute = getInitialRoute()

            if (updateLanguage(initialSettings.language)) return@launch

            navigationManager.setInitialRoute(initialRoute)

            setContent {
                val appSettings = appSettingsPreferences.settings.collectAsStateWithLifecycle(
                    initialValue = initialSettings
                )

                HealthTrackerTheme(appSettings = appSettings.value) {
                    AppNavigation(navigationManager = navigationManager)
                }
            }
        }
    }

    private suspend fun getInitialRoute(): AppNavKey {
        val email = sessionManager.getCurrentUserEmail()

        if (email.isBlank()) {
            return StartRoute
        }

        val profile = userProfileRepository.getProfile(email)
        return if (profile == null) SetupProfileRoute else HomeRoute
    }

    private fun updateLanguage(language: AppLanguage): Boolean {
        val locales = LocaleListCompat.forLanguageTags(language.code)
        if (AppCompatDelegate.getApplicationLocales() == locales) return false

        AppCompatDelegate.setApplicationLocales(locales)
        return true
    }
}
