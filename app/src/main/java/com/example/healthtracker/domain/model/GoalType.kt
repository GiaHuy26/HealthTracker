package com.example.healthtracker.domain.model

import com.example.healthtracker.R

enum class GoalType(val caloriesOffset: Int, val titleResId: Int) {
    LOSE_WEIGHT(-500, R.string.goal_lose_weight),
    MAINTAIN_WEIGHT(0, R.string.goal_maintain_weight),
    GAIN_WEIGHT(500, R.string.goal_gain_weight)
}