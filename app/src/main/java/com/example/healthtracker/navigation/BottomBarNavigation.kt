package com.example.healthtracker.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.presentation.components.BottomBars
import com.example.healthtracker.presentation.components.ButtonAdd
import com.example.healthtracker.presentation.food_diary.FoodDiaryScreen
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun BottomBarNavigation(
    navigationManager: NavigationManager
) {
    var selectedTab by remember { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = {
            BottomBars(
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    selectedTab = index
                }
            )
        },
        floatingActionButton = {
            if (selectedTab == 1) {
                ButtonAdd(
                    size = Dimens.ButtonHeight,
                    onClick = {}
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            when (selectedTab) {
                0 -> Box {}
                1 -> FoodDiaryScreen(
                    onAddMealClick = {}
                )
                2 -> Box {}
                3 -> Box {}
                4 -> Box {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBarNavigation() {
    HealthTrackerTheme {
        BottomBarNavigation(navigationManager = NavigationManager())
    }
}
