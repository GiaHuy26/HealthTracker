package com.example.healthtracker

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.data.repository.AppSettingsManager
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.navigation.AppNavigation
import com.example.healthtracker.navigation.NavigationManager
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var appSettingsManager: AppSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            val initialSettings = appSettingsManager.settings.first()
            changeLanguage(initialSettings.language)

            setContent {
                val appSettings = appSettingsManager.settings.collectAsStateWithLifecycle(
                    initialValue = initialSettings
                )

                HealthTrackerTheme(appSettings = appSettings.value) {
                    AppNavigation(navigationManager = navigationManager)
                }
            }
        }
    }

    private fun changeLanguage(language: AppLanguage) {
        val locale = Locale(language.code)
        Locale.setDefault(locale)

        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
