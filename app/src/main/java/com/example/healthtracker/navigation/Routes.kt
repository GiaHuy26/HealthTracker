package com.example.healthtracker.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey

@Serializable
data object StartRoute: AppNavKey

@Serializable
data object LoginRoute: AppNavKey

@Serializable
data object SignUpRoute: AppNavKey

@Serializable

data object HomeRoute: AppNavKey

@Serializable
data object SetupProfileRoute: AppNavKey

@Serializable
data class AddFoodRoute(
    val date: String,
    val mealType: String? = null
) : AppNavKey
