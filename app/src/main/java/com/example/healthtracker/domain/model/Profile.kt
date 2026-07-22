package com.example.healthtracker.domain.model

import com.example.healthtracker.R
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
        get() = birthDate.toAge()

    val bmi: Float
        get() {
            if (height <= 0f) return 0f
            val heightInMeters = height / 100f
            return weight / (heightInMeters * heightInMeters)
        }

    val bmiLevel: BmiLevel
        get() = BmiLevel.fromBmi(bmi)

    val bmr: Float
        get() {
            val base = (10 * weight) + (6.25f * height) - (5 * (age ?: 0))
            return if (gender == Gender.MALE) base + 5 else base - 161
        }

    val tdee: Float
        get() = bmr * activeLevel.multiplier

    val dailyCalorieTarget: Float
        get() = (tdee + goalType.caloriesOffset).coerceAtLeast(0f)
}

enum class BmiLevel(val titleResId: Int) {
    UNDERWEIGHT(R.string.settings_bmi_underweight),
    NORMAL(R.string.settings_bmi_normal),
    OVERWEIGHT(R.string.settings_bmi_overweight),
    OBESE(R.string.settings_bmi_obese);

    companion object {
        fun fromBmi(bmi: Float): BmiLevel {
            return when {
                bmi < 18.5f -> UNDERWEIGHT
                bmi < 25f -> NORMAL
                bmi < 30f -> OVERWEIGHT
                else -> OBESE
            }
        }
    }
}

fun String.toAge(): Int? {
    if (!matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) return null

    return try {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US).apply {
            isLenient = false
        }
        val parsedDate = dateFormat.parse(this) ?: return null
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance().apply { time = parsedDate }

        if (birthDate.after(today)) return null

        var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        age.takeIf { it in 1..120 }
    } catch (exception: Exception) {
        null
    }
}
