package com.example.healthtracker.presentation.food_diary.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.LunchDining
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun MealSelected(
    selectedMeal: MealType?,
    onMealSelected: (MealType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
    ) {
        MealType.entries.forEach { type ->
            val isSelected = selectedMeal == type
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.CardHeightMedium),
                onClick = { onMealSelected(type) },
                shape = RoundedCornerShape(Dimens.CornerMedium),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(Dimens.SpaceSmall),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        when(type){
                            MealType.BREAKFAST-> Icons.Outlined.WbSunny
                            MealType.LUNCH-> Icons.Outlined.Cloud
                            MealType.DINNER->Icons.Outlined.Nightlight
                            MealType.SNACK->Icons.Outlined.LunchDining
                        },
                        contentDescription = null,
                        tint = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                    Spacer(Modifier.height(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(type.titleResId),
                        style = MaterialTheme.typography.bodySmall,
                        color =  if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGoalSelector() {
    HealthTrackerTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            MealSelected(
                selectedMeal = MealType.BREAKFAST,
                onMealSelected = {}
            )
        }
    }
}