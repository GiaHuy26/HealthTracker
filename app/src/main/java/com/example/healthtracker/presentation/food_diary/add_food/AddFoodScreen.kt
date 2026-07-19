package com.example.healthtracker.presentation.food_diary.add_food

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.food_diary.component.ChooseFoodItem
import com.example.healthtracker.presentation.food_diary.component.MealSelected
import com.example.healthtracker.presentation.food_diary.component.SearchFood
import com.example.healthtracker.presentation.food_diary.component.SelectedFoodItemRow
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun AddFoodScreen(
    date: String,
    mealType: String?,
    viewModel: AddFoodViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(date, mealType) {
        viewModel.initData(date, mealType)
    }

    LaunchedEffect(Unit) {
        viewModel.saveEvent.collect {
            onBackClick()
        }
    }

    AddFoodContent(
        uiState = uiState,
        onSelectedMeal = viewModel::onSelectedMeal,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSavingMeal = viewModel::onSavingMeal,
        onAddFoodClick = viewModel::onAddFoodClick,
        onRemoveAllClick = viewModel::onRemoveAllClick,
        onIncrementQuantity = viewModel::onIncrementQuantity,
        onDecrementQuantity = viewModel::onDecrementQuantity,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodContent(
    uiState: AddFoodUiState = AddFoodUiState(),
    onSelectedMeal: (MealType) -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    onSavingMeal: () -> Unit = {},
    onAddFoodClick: (FoodEntity) -> Unit = {},
    onRemoveAllClick: () -> Unit = {},
    onIncrementQuantity: (SelectedFoodItem) -> Unit = {},
    onDecrementQuantity: (SelectedFoodItem) -> Unit = {},
    onBackClick: () -> Unit = {}
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
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                    }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Spacer(Modifier.height(Dimens.SpaceSmall))
            Text(
                text = stringResource(R.string.food_diary_choose_meal),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            MealSelected(
                selectedMeal = uiState.selectedMealType,
                onMealSelected = onSelectedMeal
            )
            Spacer(Modifier.height(Dimens.SpaceMedium))
            Text(
                text = stringResource(R.string.food_diary_choose_food),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            SearchFood(
                text = stringResource(R.string.food_diary_search_label),
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange
            )
            Spacer(Modifier.height(Dimens.SpaceMedium))

            val displayedFoods = if (uiState.searchQuery.isEmpty()) {
                uiState.listFoods
            } else {
                uiState.searchResults
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.CornerMedium),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(
                    Dimens.BorderStrokeSmall,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SpaceMedium)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.HeightCard),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                    ) {
                        items(displayedFoods, key = { it.id }) { food ->
                            ChooseFoodItem(
                                food = food,
                                onAddClick = { onAddFoodClick(food) }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(Dimens.SpaceMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.food_diary_selected_foods_title,
                        uiState.selectedFoods.size
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.food_diary_clear_all),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onRemoveAllClick() }
                )
            }

            Spacer(Modifier.height(Dimens.SpaceSmall))

            if (uiState.selectedFoods.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.HeightCardSmall),
                    shape = RoundedCornerShape(Dimens.CornerMedium),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    border = BorderStroke(
                        Dimens.BorderStrokeSmall,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(Dimens.SpaceMedium)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(Dimens.EmptyStateIconBg)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.ShoppingBag,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                    modifier = Modifier.size(Dimens.IconLarge)
                                )
                            }
                            Spacer(Modifier.height(Dimens.SpaceSmall))
                            Text(
                                text = stringResource(R.string.food_diary_no_food_selected),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(Dimens.SpaceExtraSmall))
                            Text(
                                text = stringResource(R.string.food_diary_empty_selected_desc),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.CornerMedium),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    border = BorderStroke(
                        Dimens.BorderStrokeSmall,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.SpaceMedium),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                    ) {
                        uiState.selectedFoods.forEach { item ->
                            SelectedFoodItemRow(
                                item = item,
                                onIncrement = { onIncrementQuantity(item) },
                                onDecrement = { onDecrementQuantity(item) }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(Dimens.SpaceLarge))
        }
    }
}

@Preview
@Composable
fun PreviewAddFoodContent() {
    HealthTrackerTheme {
        AddFoodContent()
    }
}