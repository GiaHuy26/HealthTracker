package com.example.healthtracker.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ThemeBrightness {
    LIGHT, DARK
}

enum class ColorPreset {
    GREEN, BLUE, PURPLE, ORANGE, PINK, TEAL
}

data class ThemeConfig(
    val brightness: ThemeBrightness = ThemeBrightness.LIGHT,
    val colorPreset: ColorPreset = ColorPreset.GREEN,
    val textSizePreset: TextSizePreset = TextSizePreset.MEDIUM
)

private fun getLightColorScheme(preset: ColorPreset): ColorScheme {
    val primaryColor = when (preset) {
        ColorPreset.GREEN -> HealthGreen
        ColorPreset.BLUE -> HealthBlue
        ColorPreset.PURPLE -> HealthPurple
        ColorPreset.ORANGE -> HealthOrange
        ColorPreset.PINK -> HealthPink
        ColorPreset.TEAL -> HealthTeal
    }

    return lightColorScheme(
        primary = primaryColor,
        onPrimary = Color.White,
        secondary = HealthGreenDark  ,
        onSecondary = Color.White,
        primaryContainer = primaryColor.copy(alpha = 0.2f),
        background = HealthBgLight,
        onBackground = HealthTextDark,
        surface = HealthCardLight,
        onSurface = HealthTextDark,
        surfaceVariant = HealthContainerLight,
        onSurfaceVariant = HealthTextMutedGreen,
        outline = HealthDividerLight,
        error = HealthError,
        onError = Color.White
    )
}

private fun getDarkColorScheme(preset: ColorPreset): ColorScheme {
    val primaryColor = when (preset) {
        ColorPreset.GREEN -> HealthGreen
        ColorPreset.BLUE -> HealthBlue
        ColorPreset.PURPLE -> HealthPurple
        ColorPreset.ORANGE -> HealthOrange
        ColorPreset.PINK -> HealthPink
        ColorPreset.TEAL -> HealthTeal
    }

    return darkColorScheme(
        primary = primaryColor,
        onPrimary = HealthBgDark,
        secondary = HealthBgDark,
        onSecondary = HealthTextLight,
        primaryContainer = primaryColor.copy(alpha = 0.2f),
        background = HealthBgDark,
        onBackground = HealthTextLight,
        surface = HealthCardDark,
        onSurface = HealthTextLight,
        surfaceVariant = HealthBgDark,
        onSurfaceVariant = HealthTextMuted,
        outline = HealthDividerDark,
        error = HealthError,
        onError = Color.White
    )
}

private fun getColorScheme(preset: ColorPreset, isDark: Boolean) = 
    if (isDark) getDarkColorScheme(preset) else getLightColorScheme(preset)

@Composable
fun HealthTrackerTheme(
    themeConfig: ThemeConfig = ThemeConfig(),
    content: @Composable () -> Unit
) {
    val isDark = themeConfig.brightness == ThemeBrightness.DARK
    val colorScheme = getColorScheme(themeConfig.colorPreset, isDark)
    val typography = getScaledTypography(themeConfig.textSizePreset)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}