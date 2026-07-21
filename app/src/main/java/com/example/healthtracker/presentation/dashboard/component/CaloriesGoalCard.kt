package com.example.healthtracker.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.presentation.dashboard.DashboardUiState
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthLightGreen

@Composable
fun CaloriesGoalCard(uiState: DashboardUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.DashboardGoalCardHeight),
        shape = RoundedCornerShape(Dimens.CornerExtraLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.ElevationMedium),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(HealthLightGreen, HealthBlue)
                    )
                )
                .padding(Dimens.CardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.dashboard_today_goal),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.size(Dimens.CircularProgressLarge),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { uiState.progressFloat },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                    strokeWidth = Dimens.CircularProgressStroke,
                    strokeCap = StrokeCap.Round
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = uiState.calorieBalance.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = stringResource(
                            R.string.dashboard_calorie_goal_format,
                            uiState.targetCalories
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = when {
                    uiState.remainingCalories >= 0 -> stringResource(
                        R.string.dashboard_remaining_calories,
                        uiState.remainingCalories
                    )
                    else -> stringResource(
                        R.string.dashboard_exceeded_calories,
                        -uiState.remainingCalories
                    )
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
