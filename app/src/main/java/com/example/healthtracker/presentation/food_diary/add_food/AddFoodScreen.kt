package com.example.healthtracker.presentation.food_diary.add_food

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.food_diary.component.MealSelected
import com.example.healthtracker.presentation.food_diary.component.SearchFood
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun AddFoodScreen() {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodContent(
    uiState: AddFoodUiState = AddFoodUiState(),
    onSelectedMeal: (MealType) -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    onSavingMeal: () -> Unit = {},
    onAddFood: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.food_diary_add_food),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.ScreenPadding)
            ) {
                Button(
                    text = stringResource(R.string.food_diary_btn_save_meal),
                    onClick = onSavingMeal
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Text(
                text = stringResource(R.string.food_diary_choose_meal),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            MealSelected(
                selectedMeal = uiState.selectedMealType,
                onMealSelected = onSelectedMeal
            )
            Text(
                text = stringResource(R.string.food_diary_choose_food),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            SearchFood(
                text = stringResource(R.string.food_diary_search_label),
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddFoodContent() {
    HealthTrackerTheme() {
        AddFoodContent()
    }
}