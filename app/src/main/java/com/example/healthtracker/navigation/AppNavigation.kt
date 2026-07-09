package com.example.healthtracker.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.healthtracker.presentation.login.LoginScreen
import com.example.healthtracker.presentation.signup.SignUpScreen
import com.example.healthtracker.presentation.start.StartScreen

@Composable
fun AppNavigation(
    navigationManager: NavigationManager
) {
    val backStack = navigationManager.backStack.collectAsStateWithLifecycle().value
    NavDisplay(
        backStack = backStack,
        onBack = { navigationManager.navigateBack() },
        entryProvider = { route ->
            when (route) {
                is StartRoute -> NavEntry(route) {
                    StartScreen(navigationManager = navigationManager)
                }

                is LoginRoute -> NavEntry(route) {
                  LoginScreen(navigationManager = navigationManager)
                }

                is SignUpRoute -> NavEntry(route) {
                    SignUpScreen(navigationManager = navigationManager)
                }

                is HomeRoute -> NavEntry(route) {
                    Text("Home Screen")
                }
            }
        }
    )
}