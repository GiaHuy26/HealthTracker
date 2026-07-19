package com.example.healthtracker.presentation.food_diary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun MealCard(
    mealName: String,
    totalCalories: Int,
    foods: List<MealEntity>,
    onAddFood: () -> Unit
) {
    Cards {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = mealName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$totalCalories kcal",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.height(Dimens.SpaceMedium))
        HorizontalDivider()
        Spacer(Modifier.height(Dimens.SpaceSmall))
        if(foods.isNotEmpty()){
            Column {
                foods.forEach { food->
                    FoodItemRow(foods = food)
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))

        Button(
            text = stringResource(R.string.food_diary_add_food),
            onClick = onAddFood
        )
    }
}

@Composable
fun FoodItemRow(
    foods: MealEntity,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.BgIcon)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(
                        alpha = 0.5f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Fastfood,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(Dimens.IconNormal)
            )
        }
        Spacer(Modifier.width(Dimens.SpaceMedium))
        Column {
            Text(
                text = foods.foodName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            Text(
                text = "${foods.servingSize} • ${foods.calories} kcal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMealCard() {
    val sampleFoods = listOf(
        MealEntity(
            id = 1,
            date = "2026-07-15",
            mealType = "Breakfast",
            foodName = "Oatmeal",
            calories = 180,
            servingSize = "150g",
            quantity = 1.0f
        ),
        MealEntity(
            id = 2,
            date = "2026-07-15",
            mealType = "Breakfast",
            foodName = "Banana",
            calories = 89,
            servingSize = "100g",
            quantity = 1.0f
        ),
        MealEntity(
            id = 3,
            date = "2026-07-15",
            mealType = "Breakfast",
            foodName = "Boiled Egg",
            calories = 78,
            servingSize = "50g",
            quantity = 1.0f
        )
    )
    HealthTrackerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MealCard(
                mealName = "Bữa sáng",
                totalCalories = 347,
                foods = sampleFoods,
                onAddFood = {}
            )
        }
    }
}








