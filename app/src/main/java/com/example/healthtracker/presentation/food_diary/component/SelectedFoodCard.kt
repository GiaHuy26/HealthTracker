package com.example.healthtracker.presentation.food_diary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.presentation.food_diary.add_food.SelectedFoodItem
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun SelectedFoodItemRow(
    item: SelectedFoodItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val food = item.foods
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimens.CornerMedium)
            )
            .padding(Dimens.SpaceSmall),
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

        Spacer(Modifier.width(Dimens.SpaceSmall))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = food.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Spacer(Modifier.height(Dimens.SpaceExtraSmall))
            val text = if (item.quantity % 1f == 0f) {
                stringResource(
                    R.string.food_diary_portion_int_format,
                    item.quantity.toInt(),
                    food.servingSize
                )
            } else {
                stringResource(
                    R.string.food_diary_portion_decimal_format,
                    item.quantity,
                    food.servingSize
                )
            }
            val totalCalories = (food.calories * item.quantity).toInt()
            Text(
                text = "$text • $totalCalories kcal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }

        Spacer(Modifier.width(Dimens.SpaceSmall))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.QuantityButtonSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onDecrement() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Decrease",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimens.IconSmall)
                )
            }

            Text(
                text = if (item.quantity % 1f == 0f) item.quantity.toInt().toString() else item.quantity.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(Dimens.Width),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(Dimens.QuantityButtonSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onIncrement() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimens.IconSmall)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedFoodItemRow() {
    val sampleSelected = SelectedFoodItem(
        foods = FoodEntity(id = 1, name = "Phở bò tô nhỏ", calories = 350, servingSize = "1 tô"),
        quantity = 1.5f
    )
    HealthTrackerTheme {
        Box(modifier = Modifier.padding(Dimens.SpaceMedium)) {
            SelectedFoodItemRow(
                item = sampleSelected,
                onIncrement = {},
                onDecrement = {}
            )
        }
    }
}
