package com.example.healthtracker.domain.model

import com.example.healthtracker.R

enum class AppTheme(val titleResId: Int) {
    LIGHT(R.string.appearance_light),
    DARK(R.string.appearance_dark)
}

enum class AppColor {
    GREEN, BLUE, PURPLE, ORANGE, PINK, TEAL
}

enum class AppFontSize(val titleResId: Int, val scale: Float) {
    SMALL(R.string.appearance_font_small, 0.85f),
    MEDIUM(R.string.appearance_font_medium, 1f),
    LARGE(R.string.appearance_font_large, 1.15f)
}

enum class AppLanguage(
    val code: String,
    val titleResId: Int,
    val subtitleResId: Int
) {
    ENGLISH(
        "en",
        R.string.appearance_language_english,
        R.string.appearance_language_english_subtitle
    ),
    VIETNAMESE(
        "vi",
        R.string.appearance_language_vietnamese,
        R.string.appearance_language_vietnamese_subtitle
    )
}

data class AppSettings(
    val theme: AppTheme = AppTheme.LIGHT,
    val color: AppColor = AppColor.GREEN,
    val fontSize: AppFontSize = AppFontSize.MEDIUM,
    val language: AppLanguage = AppLanguage.VIETNAMESE
)
