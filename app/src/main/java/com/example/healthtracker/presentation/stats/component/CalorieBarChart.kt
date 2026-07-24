package com.example.healthtracker.presentation.stats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.presentation.stats.DailyCalories
import com.example.healthtracker.presentation.theme.Dimens
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CalorieBarChart(
    dailyCalories: List<DailyCalories>,
    periodDays: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(Dimens.Elevation)
    ) {
        Column(modifier = Modifier.padding(Dimens.SpaceMedium)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.statistics_calorie_overview),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (periodDays == 1) {
                        stringResource(R.string.statistics_today)
                    } else {
                        stringResource(R.string.statistics_last_days, periodDays)
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
            StatisticsChartLegend()
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))

            if (dailyCalories.isEmpty()) {
                Text(
                    text = stringResource(R.string.statistics_no_data),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.StatisticsChartHeight),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                val maxCalories = dailyCalories
                    .maxOf { maxOf(it.caloriesIn, it.caloriesBurned) }
                    .coerceAtLeast(1)
                val showAllLabels = dailyCalories.size <= 7
                val intakeColor = MaterialTheme.colorScheme.primary
                val burnedColor = MaterialTheme.colorScheme.tertiary

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.StatisticsChartHeight)
                ) {
                    dailyCalories.forEach { day ->
                        val intakeBarHeight = day.caloriesIn.toFloat() / maxCalories
                        val burnedBarHeight = day.caloriesBurned.toFloat() / maxCalories

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                val barsModifier = if (showAllLabels) {
                                    Modifier.width(Dimens.StatisticsBarWidth)
                                } else {
                                    Modifier.fillMaxWidth(0.8f)
                                }

                                Row(
                                    modifier = barsModifier.fillMaxHeight(),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        Dimens.SpaceExtraSmall
                                    ),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    StatisticsBar(
                                        value = day.caloriesIn,
                                        heightRatio = intakeBarHeight,
                                        color = intakeColor,
                                        modifier = Modifier.weight(1f)
                                    )
                                    StatisticsBar(
                                        value = day.caloriesBurned,
                                        heightRatio = burnedBarHeight,
                                        color = burnedColor,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            if (showAllLabels) {
                                Spacer(modifier = Modifier.height(Dimens.SpaceExtraSmall))
                                Text(
                                    text = formatStatisticsDate(day.date),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                if (!showAllLabels) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        val dates = listOf(
                            dailyCalories.first(),
                            dailyCalories[dailyCalories.size / 2],
                            dailyCalories.last()
                        )

                        dates.forEach { day ->
                            Text(
                                text = formatStatisticsDate(day.date),
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatisticsBar(
    value: Int,
    heightRatio: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val barModifier = if (value > 0) {
        modifier.fillMaxHeight(heightRatio.coerceIn(0f, 1f))
    } else {
        modifier.height(Dimens.BorderStrokeMedium)
    }

    Box(
        modifier = barModifier
            .clip(
                RoundedCornerShape(
                    topStart = Dimens.CornerSmall,
                    topEnd = Dimens.CornerSmall
                )
            )
            .background(
                if (value > 0) {
                    color
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            )
    )
}

@Composable
internal fun StatisticsChartLegend() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatisticsLegendItem(
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(R.string.statistics_calorie_intake)
        )
        Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
        StatisticsLegendItem(
            color = MaterialTheme.colorScheme.tertiary,
            text = stringResource(R.string.statistics_calories_burned)
        )
    }
}

@Composable
private fun StatisticsLegendItem(
    color: Color,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(Dimens.SpaceSmall)
                .clip(RoundedCornerShape(Dimens.CornerSmall))
                .background(color)
        )
        Spacer(modifier = Modifier.width(Dimens.SpaceExtraSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

internal fun formatStatisticsDate(date: String): String {
    val databaseFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val displayFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

    return try {
        val parsedDate = databaseFormat.parse(date)
        displayFormat.format(parsedDate!!)
    } catch (e: Exception) {
        date
    }
}
