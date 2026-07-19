package com.example.healthtracker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.healthtracker.presentation.login.LoginScreen
import com.example.healthtracker.presentation.setup_profile.SetupProfileScreen
import com.example.healthtracker.presentation.signup.SignUpScreen
import com.example.healthtracker.presentation.start.StartScreen
import com.example.healthtracker.presentation.food_diary.add_food.AddFoodScreen

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

                is SetupProfileRoute -> NavEntry(route){
                    SetupProfileScreen(navigationManager = navigationManager)
                }

                is HomeRoute -> NavEntry(route) {
                    BottomBarNavigation(navigationManager = navigationManager)
                }

                is AddFoodRoute -> NavEntry(route) {
                    AddFoodScreen(
                        date = route.date,
                        mealType = route.mealType,
                        onBackClick = { navigationManager.navigateBack() }
                    )
                }
            }
        }
    )
}