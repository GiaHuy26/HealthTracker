package com.example.healthtracker.presentation.setup_profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun GoalSelector(
    selectedGoal: GoalType,
    onGoalSelected: (GoalType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
    ) {
        GoalType.entries.forEach { goal ->
            val isSelected = selectedGoal == goal
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.CardHeight),
                onClick = { onGoalSelected(goal) },
                shape = RoundedCornerShape(Dimens.CornerMedium),
                border = BorderStroke(
                    width = if (isSelected) Dimens.BorderStrokeMedium else Dimens.BorderStrokeSmall,
                    color = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Gray
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        when (goal) {
                            GoalType.LOSE_WEIGHT -> Icons.AutoMirrored.Outlined.TrendingDown
                            GoalType.MAINTAIN_WEIGHT -> Icons.Outlined.Balance
                            GoalType.GAIN_WEIGHT -> Icons.AutoMirrored.Outlined.TrendingUp
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                    Spacer(Modifier.height(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(id = goal.titleResId),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}