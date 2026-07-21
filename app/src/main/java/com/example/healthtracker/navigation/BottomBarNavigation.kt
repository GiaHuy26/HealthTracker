package com.example.healthtracker.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.presentation.components.BottomBars
import com.example.healthtracker.presentation.food_diary.FoodDiaryScreen
import com.example.healthtracker.presentation.activity_diary.ActivityDiaryScreen
import com.example.healthtracker.presentation.dashboard.DashboardScreen
import com.example.healthtracker.presentation.stats.StatisticsScreen
import com.example.healthtracker.presentation.settings.SettingsScreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun BottomBarNavigation(
    navigationManager: NavigationManager
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> DashboardScreen(
                    onAddMealClick = { selectedTab = 1 },
                    onAddActivityClick = { selectedTab = 2 },
                    onViewAllMealsClick = { selectedTab = 1 },
                    onProfileClick = { selectedTab = 4 }
                )
                1 -> FoodDiaryScreen(
                    onAddMealClick = { date, mealType ->
                        navigationManager.navigateTo(
                            AddFoodRoute(date = date, mealType = mealType)
                        )
                    }
                )
                2 -> ActivityDiaryScreen()
                3 -> StatisticsScreen()
                4 -> SettingsScreen(
                    onLogoutComplete = {
                        navigationManager.navigateAndClearStack(LoginRoute)
                    }
                )
            }
        }

        BottomBars(
            selectedTab = selectedTab,
            onTabSelected = { index ->
                selectedTab = index
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBarNavigation() {
    HealthTrackerTheme {
        BottomBarNavigation(navigationManager = NavigationManager())
    }
}
