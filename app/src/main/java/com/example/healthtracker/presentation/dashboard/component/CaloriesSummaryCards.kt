package com.example.healthtracker.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.presentation.dashboard.DashboardUiState
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthGreenDark
import com.example.healthtracker.presentation.theme.HealthTeal

@Composable
fun CaloriesSummaryCards(uiState: DashboardUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
    ) {
        CaloriesSummaryCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Restaurant,
            label = stringResource(R.string.dashboard_calories_in),
            value = uiState.caloriesConsumed,
            iconColor = HealthGreenDark
        )
        CaloriesSummaryCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.LocalFireDepartment,
            label = stringResource(R.string.dashboard_calories_burned),
            value = uiState.caloriesBurned,
            iconColor = HealthTeal
        )
        CaloriesSummaryCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Flag,
            label = stringResource(R.string.dashboard_calorie_balance),
            value = uiState.calorieBalance,
            iconColor = HealthBlue
        )
    }
}

@Composable
private fun CaloriesSummaryCard(
    modifier: Modifier,
    icon: ImageVector,
    label: String,
    value: Int,
    iconColor: Color
) {
    Card(
        modifier = modifier.height(Dimens.DashboardSummaryCardHeight),
        shape = RoundedCornerShape(Dimens.CornerLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Elevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.SpaceSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Dimens.IconMedium),
                tint = iconColor
            )
            Spacer(modifier = Modifier.height(Dimens.SpaceExtraSmall))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
