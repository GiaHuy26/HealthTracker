package com.example.healthtracker.domain.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Profile(
    val userName: String,
    val birthDate: String,
    val gender: Gender,
    val weight: Float,
    val height: Float,
    val activeLevel: ActivityLevel,
    val goalType: GoalType
) {
    val age: Int?
        get() = try {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val parsedBirthDate = sdf.parse(birthDate)
            if (parsedBirthDate != null) {
                val today = Calendar.getInstance()
                val birth = Calendar.getInstance().apply { time = parsedBirthDate }
                var ageVal = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
                if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                    ageVal--
                }
                if (ageVal < 0) 0 else ageVal
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    val bmi: Float
        get() {
            if (height <= 0f) return 0f
            val heightInMeters = height / 100f
            return weight / (heightInMeters * heightInMeters)
        }

    val bmiLevel: String
        get() {
            val lBmi = bmi
            return when {
                lBmi < 18.5f -> ""
                lBmi < 25f -> ""
                lBmi < 30f -> ""
                else -> ""
            }
        }

    val bmr: Float
        get() {
            val base = (10 * weight) + (6.25f * height) - (5 * (age ?: 0))
            return if (gender == Gender.MALE) base + 5 else base - 161
        }

    val tdee: Float
        get() {
            val activityMultiplier = when (activeLevel) {
                ActivityLevel.SEDENTARY -> 1.2f
                ActivityLevel.LIGHTLY_ACTIVE -> 1.375f
                ActivityLevel.MODERATELY_ACTIVE -> 1.55f
                ActivityLevel.VERY_ACTIVE -> 1.725f
                ActivityLevel.EXTRA_ACTIVE -> 1.9f
            }
            return bmr * activityMultiplier
        }
}