package com.example.healthtracker.presentation.stats.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.presentation.stats.DailyCalories
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun CalorieTrendChart(dailyCalories: List<DailyCalories>) {
    val intakeLineColor = MaterialTheme.colorScheme.primary
    val burnedLineColor = MaterialTheme.colorScheme.tertiary

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(Dimens.Elevation)
    ) {
        Column(modifier = Modifier.padding(Dimens.SpaceMedium)) {
            Text(
                text = stringResource(R.string.statistics_trend),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
            StatisticsChartLegend()
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))

            if (dailyCalories.isEmpty()) {
                Text(
                    text = stringResource(R.string.statistics_no_data),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.StatisticsTrendChartHeight),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.StatisticsTrendChartHeight)
                ) {
                    val maxCalories = dailyCalories
                        .maxOf { maxOf(it.caloriesIn, it.caloriesBurned) }
                        .coerceAtLeast(1)
                    val padding = Dimens.SpaceSmall.toPx()
                    val chartHeight = size.height - padding * 2
                    val distance = if (dailyCalories.size > 1) {
                        size.width / (dailyCalories.size - 1)
                    } else {
                        0f
                    }

                    val intakePoints = mutableListOf<Offset>()
                    val burnedPoints = mutableListOf<Offset>()

                    dailyCalories.forEachIndexed { index, day ->
                        val x = if (dailyCalories.size == 1) {
                            size.width / 2
                        } else {
                            index * distance
                        }
                        val intakeProgress = day.caloriesIn.toFloat() / maxCalories
                        val burnedProgress = day.caloriesBurned.toFloat() / maxCalories

                        intakePoints.add(
                            Offset(
                                x = x,
                                y = padding + chartHeight * (1 - intakeProgress)
                            )
                        )
                        burnedPoints.add(
                            Offset(
                                x = x,
                                y = padding + chartHeight * (1 - burnedProgress)
                            )
                        )
                    }

                    for (index in 0 until intakePoints.lastIndex) {
                        drawLine(
                            color = intakeLineColor,
                            start = intakePoints[index],
                            end = intakePoints[index + 1],
                            strokeWidth = Dimens.BorderStrokeMedium.toPx()
                        )
                        drawLine(
                            color = burnedLineColor,
                            start = burnedPoints[index],
                            end = burnedPoints[index + 1],
                            strokeWidth = Dimens.BorderStrokeMedium.toPx()
                        )
                    }

                    intakePoints.forEach { point ->
                        drawCircle(
                            color = intakeLineColor,
                            radius = Dimens.SpaceExtraSmall.toPx(),
                            center = point
                        )
                    }
                    burnedPoints.forEach { point ->
                        drawCircle(
                            color = burnedLineColor,
                            radius = Dimens.SpaceExtraSmall.toPx(),
                            center = point
                        )
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    if (dailyCalories.size <= 7) {
                        dailyCalories.forEach { day ->
                            Text(
                                text = formatStatisticsDate(day.date),
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        val middleDay = dailyCalories[dailyCalories.size / 2]
                        val dates = listOf(
                            dailyCalories.first(),
                            middleDay,
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
