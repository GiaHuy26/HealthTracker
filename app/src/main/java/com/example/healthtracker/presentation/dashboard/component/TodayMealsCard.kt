package com.example.healthtracker.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun TodayMealsCard(
    meals: List<MealEntity>,
    onViewAllClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerExtraLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Elevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimens.SpaceMedium,
                        top = Dimens.SpaceSmall,
                        end = Dimens.SpaceSmall,
                        bottom = Dimens.SpaceExtraSmall
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dashboard_today_meals),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = onViewAllClick) {
                    Text(
                        text = stringResource(R.string.dashboard_view_all),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = Dimens.SpaceMedium),
                color = MaterialTheme.colorScheme.outline
            )

            val mealsByType = mutableListOf<Pair<MealType, List<MealEntity>>>()

            for (mealType in MealType.entries) {
                val foods = meals.filter { meal ->
                    meal.mealType.equals(mealType.name, ignoreCase = true)
                }

                if (foods.isNotEmpty()) {
                    mealsByType.add(Pair(mealType, foods))
                }
            }

            if (mealsByType.isEmpty()) {
                Text(
                    text = stringResource(R.string.dashboard_no_meals),
                    modifier = Modifier.padding(Dimens.SpaceMedium),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                mealsByType.forEachIndexed { index, (mealType, foods) ->
                    MealSummaryRow(
                        mealName = stringResource(mealType.titleResId),
                        foods = foods
                    )
                    if (index < mealsByType.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = Dimens.SpaceMedium),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MealSummaryRow(
    mealName: String,
    foods: List<MealEntity>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.SpaceMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.BgIcon)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Restaurant,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(Dimens.IconNormal)
            )
        }

        Spacer(modifier = Modifier.width(Dimens.SpaceMedium))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = mealName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = foods.joinToString { it.foodName },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = stringResource(
                R.string.dashboard_calories_format,
                foods.sumOf { it.calories }
            ),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
