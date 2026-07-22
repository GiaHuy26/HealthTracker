package com.example.healthtracker.presentation.stats

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.StatisticsPeriod
import com.example.healthtracker.presentation.stats.component.CalorieBarChart
import com.example.healthtracker.presentation.stats.component.CalorieTrendChart
import com.example.healthtracker.presentation.stats.component.DetailedMetrics
import com.example.healthtracker.presentation.stats.component.StatisticsSummaryCard
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refreshStatistics()
    }

    StatisticsContent(
        uiState = uiState,
        onPeriodSelected = viewModel::selectPeriod
    )
}

@Composable
fun StatisticsContent(
    uiState: StatisticsUiState = StatisticsUiState(),
    onPeriodSelected: (StatisticsPeriod) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            Text(
                text = stringResource(R.string.statistics_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = stringResource(R.string.statistics_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimens.SpaceExtraSmall),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceExtraSmall)
            ) {
                StatisticsPeriod.entries.forEach { period ->
                    val isSelected = uiState.selectedPeriod == period
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
                            .background(
                                if (isSelected) {
                                    MaterialTheme.colorScheme.secondary
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            )
                            .clickable { onPeriodSelected(period) }
                            .padding(vertical = Dimens.SpaceSmall),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(period.titleResId),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.onSecondary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            StatisticsSummaryCard(
                title = stringResource(uiState.selectedPeriod.summaryTitleResId),
                uiState = uiState
            )

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            CalorieBarChart(
                dailyCalories = uiState.dailyCalories,
                periodDays = uiState.selectedPeriod.numberOfDays
            )

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            CalorieTrendChart(uiState.dailyCalories)

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            Text(
                text = stringResource(R.string.statistics_detailed_metrics),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
            DetailedMetrics(uiState)
            Spacer(modifier = Modifier.height(Dimens.StatisticsBottomContentPadding))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatisticsPreview() {
    HealthTrackerTheme {
        StatisticsContent(
            uiState = StatisticsUiState(isLoading = false)
        )
    }
}
