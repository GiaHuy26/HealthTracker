package com.example.healthtracker.presentation.food_diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.components.BottomBars
import com.example.healthtracker.presentation.components.ButtonAdd
import com.example.healthtracker.presentation.components.HealthCards
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthLightGreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun FoodDiaryScreen() {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDiaryContent(
    uiState: FoodDiaryUiState = FoodDiaryUiState(),
    onCalendar: () -> Unit = {},
    onAddMeal: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(
                title = {
                    Column() {
                        Text(
                            text = stringResource(R.string.food_diary_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(R.string.food_diary_date_format_pattern),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outline)
                            .padding(Dimens.SpaceSmall)
                    ) {
                        Icon(
                            Icons.Outlined.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomBars(
                selectedTab = 1,
                onTabSelected = {}
            )
        },
        floatingActionButton = {
            ButtonAdd(
                size = Dimens.ButtonHeight,
                onClick = onAddMeal
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            HealthCards {
                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.food_diary_today_total_calories),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.background
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${uiState.totalCalories}",
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                            Spacer(Modifier.width(Dimens.SpaceSmall))
                            Text(
                                text = stringResource(R.string.food_diary_custom_calories_label),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        Row {
                            Text(
                                text = stringResource(R.string.food_diary_target),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                            Spacer(Modifier.width(Dimens.SpaceSmall))
                            Text(
                                text = "${uiState.targetCalories}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                            Spacer(Modifier.width(Dimens.SpaceSmall))
                            Text(
                                text = stringResource(R.string.food_diary_custom_calories_label),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { uiState.progressFloat },
                            modifier = Modifier.size(Dimens.CircularProgress),
                            color = MaterialTheme.colorScheme.background,
                            strokeWidth = 10.dp,
                            trackColor = HealthLightGreen,
                            strokeCap = StrokeCap.Round,
                        )
                        Text(
                            text = "${uiState.progressPercentage}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.background,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFoodDiary() {
    HealthTrackerTheme() {
        FoodDiaryContent()
    }
}
