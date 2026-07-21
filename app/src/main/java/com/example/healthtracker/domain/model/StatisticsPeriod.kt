package com.example.healthtracker.domain.model

import com.example.healthtracker.R

enum class StatisticsPeriod(
    val numberOfDays: Int,
    val titleResId: Int,
    val summaryTitleResId: Int
) {
    DAY(
        1,
        R.string.statistics_period_day,
        R.string.statistics_daily_summary
    ),
    WEEK(
        7,
        R.string.statistics_period_week,
        R.string.statistics_weekly_summary
    ),
    MONTH(
        30,
        R.string.statistics_period_month,
        R.string.statistics_monthly_summary
    )
}
