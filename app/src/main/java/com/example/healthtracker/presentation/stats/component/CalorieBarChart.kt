package com.example.healthtracker.presentation.stats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
                    text = stringResource(R.string.statistics_calorie_intake),
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
                    .maxOf { it.caloriesIn }
                    .coerceAtLeast(1)
                val showAllLabels = dailyCalories.size <= 7

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.StatisticsChartHeight)
                ) {
                    dailyCalories.forEach { day ->
                        val barHeight = day.caloriesIn.toFloat() / maxCalories

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (showAllLabels) {
                                Text(
                                    text = day.caloriesIn.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                val barModifier = if (showAllLabels) {
                                    Modifier.width(Dimens.StatisticsBarWidth)
                                } else {
                                    Modifier.fillMaxWidth(0.6f)
                                }

                                if (day.caloriesIn > 0) {
                                    Box(
                                        modifier = barModifier
                                            .fillMaxHeight(barHeight)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = Dimens.CornerSmall,
                                                    topEnd = Dimens.CornerSmall
                                                )
                                            )
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                } else {
                                    Box(
                                        modifier = barModifier
                                            .height(Dimens.BorderStrokeMedium)
                                            .background(
                                                MaterialTheme.colorScheme.outlineVariant
                                            )
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
