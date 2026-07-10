package com.example.healthtracker.domain.model

import com.example.healthtracker.R


enum class ActivityLevel(val multiplier: Float, val titleResId: Int, val descriptionResId: Int) {
    SEDENTARY(1.2f, R.string.activity_sedentary_title, R.string.activity_sedentary_desc),
    LIGHTLY_ACTIVE(1.375f, R.string.activity_light_title, R.string.activity_light_desc),
    MODERATELY_ACTIVE(1.55f, R.string.activity_moderate_title, R.string.activity_moderate_desc),
    VERY_ACTIVE(1.725f, R.string.activity_very_title, R.string.activity_very_desc),
    EXTRA_ACTIVE(1.9f, R.string.activity_extra_title, R.string.activity_extra_desc)
}