package com.example.healthtracker.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.healthtracker.domain.model.AppColor
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.AppTheme

private fun getPrimaryColor(preset: AppColor): Color {
    return when (preset) {
        AppColor.GREEN -> HealthGreenDark
        AppColor.BLUE -> HealthBlue
        AppColor.PURPLE -> HealthPurple
        AppColor.ORANGE -> HealthOrange
        AppColor.PINK -> HealthPink
        AppColor.TEAL -> HealthTeal
    }
}

private fun getGradientEndColor(preset: AppColor): Color {
    return when (preset) {
        AppColor.GREEN -> HealthBlue
        AppColor.BLUE -> HealthTeal
        AppColor.PURPLE -> HealthPink
        AppColor.ORANGE -> HealthPink
        AppColor.PINK -> HealthPurple
        AppColor.TEAL -> HealthBlue
    }
}

private fun getLightColorScheme(preset: AppColor): ColorScheme {
    val primaryColor = getPrimaryColor(preset)
    val gradientEndColor = getGradientEndColor(preset)

    return lightColorScheme(
        primary = primaryColor,
        onPrimary = Color.White,
        secondary = primaryColor,
        onSecondary = Color.White,
        tertiary = gradientEndColor,
        onTertiary = Color.White,
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

private fun getDarkColorScheme(preset: AppColor): ColorScheme {
    val primaryColor = getPrimaryColor(preset)
    val gradientEndColor = getGradientEndColor(preset)

    return darkColorScheme(
        primary = primaryColor,
        onPrimary = HealthBgDark,
        secondary = primaryColor,
        onSecondary = HealthBgDark,
        tertiary = gradientEndColor,
        onTertiary = HealthBgDark,
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

private fun getColorScheme(preset: AppColor, isDark: Boolean) =
    if (isDark) getDarkColorScheme(preset) else getLightColorScheme(preset)

@Composable
fun HealthTrackerTheme(
    appSettings: AppSettings = AppSettings(),
    content: @Composable () -> Unit
) {
    val isDark = when (appSettings.theme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
    }
    val colorScheme = getColorScheme(appSettings.color, isDark)
    val typography = getScaledTypography(appSettings.fontSize)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
