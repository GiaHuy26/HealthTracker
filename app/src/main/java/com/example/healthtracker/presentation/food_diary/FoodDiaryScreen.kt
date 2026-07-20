package com.example.healthtracker.presentation.food_diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import android.app.DatePickerDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.presentation.components.ButtonAdd
import com.example.healthtracker.presentation.components.HealthCards
import com.example.healthtracker.presentation.food_diary.component.MealCard
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthLightGreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun FoodDiaryScreen(
    viewModel: FoodDiaryViewModel = hiltViewModel(),
    onAddMealClick: (date: String, mealType: String?) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    FoodDiaryContent(
        uiState = uiState,
        onCalendar = {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val currentDate = try {
                dateFormat.parse(uiState.selectDate)
            } catch (exception: Exception) {
                null
            } ?: Date()
            val currentCalendar = Calendar.getInstance().apply {
                time = currentDate
            }

            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    viewModel.selectDateByMillis(selectedCalendar.timeInMillis)
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        },
        onAddMeal = { mealType ->
            onAddMealClick(uiState.selectDate, mealType?.name)
        },
        onAddFoodClick = {
            onAddMealClick(uiState.selectDate, null)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDiaryContent(
    uiState: FoodDiaryUiState = FoodDiaryUiState(),
    onCalendar: () -> Unit = {},
    onAddMeal: (MealType?) -> Unit = {},
    onAddFoodClick: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val displayDate = formatFoodDiaryDate(uiState.selectDate)

    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.food_diary_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = displayDate,
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
                            .clickable { onCalendar() }
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
        floatingActionButton = {
            ButtonAdd(
                size = Dimens.ButtonHeight,
                onClick = onAddFoodClick,
                modifier = Modifier.padding(bottom = Dimens.FloatingActionButton)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
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
                            color = HealthLightGreen,
                            strokeWidth = 10.dp,
                            trackColor = MaterialTheme.colorScheme.background,
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
            Spacer(Modifier.height(Dimens.SpaceMedium))
            MealType.entries.forEach { type ->
                val foodsMeal =
                    uiState.meal.filter { it.mealType.equals(type.name, ignoreCase = true) }
                if (foodsMeal.isNotEmpty()) {
                    MealCard(
                        mealName = stringResource(type.titleResId),
                        totalCalories = foodsMeal.sumOf { it.calories },
                        foods = foodsMeal,
                        onAddFood = { onAddMeal(type) }
                    )
                    Spacer(Modifier.height(Dimens.SpaceMedium))
                }
            }
        }
    }
}

@Composable
private fun formatFoodDiaryDate(dateString: String): String {
    val configuration = LocalConfiguration.current
    val pattern = stringResource(R.string.food_diary_date_format_pattern)
    val locale = configuration.locales[0]

    return remember(dateString, pattern, locale) {
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
            isLenient = false
        }
        val displayDateFormat = SimpleDateFormat(pattern, locale)
        val date = runCatching {
            databaseDateFormat.parse(dateString)
        }.getOrNull() ?: Date()

        displayDateFormat.format(date)
    }
}

@Preview
@Composable
fun PreviewFoodDiary() {
    HealthTrackerTheme() {
        FoodDiaryContent()
    }
}
