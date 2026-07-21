package com.example.healthtracker.presentation.stats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.TrackChanges
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
import com.example.healthtracker.presentation.stats.StatisticsUiState
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthOrange
import com.example.healthtracker.presentation.theme.HealthTeal

@Composable
fun DetailedMetrics(uiState: StatisticsUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
    ) {
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Restaurant,
            title = stringResource(R.string.statistics_average_intake_short),
            value = stringResource(
                R.string.statistics_calories_value,
                uiState.averageCaloriesIn
            ),
            iconColor = HealthBlue
        )
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.LocalFireDepartment,
            title = stringResource(R.string.statistics_average_burned_short),
            value = stringResource(
                R.string.statistics_calories_value,
                uiState.averageCaloriesBurned
            ),
            iconColor = HealthTeal
        )
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.TrackChanges,
            title = stringResource(R.string.statistics_target_days),
            value = stringResource(
                R.string.statistics_target_days_value,
                uiState.targetDays,
                uiState.selectedPeriod.numberOfDays
            ),
            iconColor = HealthOrange
        )
    }
}

@Composable
private fun MetricCard(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    value: String,
    iconColor: Color
) {
    Card(
        modifier = modifier.height(Dimens.StatisticsMetricCardHeight),
        shape = RoundedCornerShape(Dimens.CornerMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Elevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.SpaceSmall),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}
