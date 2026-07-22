package com.example.healthtracker.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.AppFontSize

val MontserratFontFamily = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

val BaseTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    displayMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)

fun getScaledTypography(fontSize: AppFontSize): Typography {
    val factor = fontSize.scale
    return Typography(
        displayLarge = BaseTypography.displayLarge.copy(
            fontSize = (BaseTypography.displayLarge.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.displayLarge.lineHeight.value * factor).sp
        ),
        displayMedium = BaseTypography.displayMedium.copy(
            fontSize = (BaseTypography.displayMedium.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.displayMedium.lineHeight.value * factor).sp
        ),
        displaySmall = BaseTypography.displaySmall.copy(
            fontSize = (BaseTypography.displaySmall.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.displaySmall.lineHeight.value * factor).sp
        ),
        headlineLarge = BaseTypography.headlineLarge.copy(
            fontSize = (BaseTypography.headlineLarge.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.headlineLarge.lineHeight.value * factor).sp
        ),
        headlineMedium = BaseTypography.headlineMedium.copy(
            fontSize = (BaseTypography.headlineMedium.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.headlineMedium.lineHeight.value * factor).sp
        ),
        headlineSmall = BaseTypography.headlineSmall.copy(
            fontSize = (BaseTypography.headlineSmall.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.headlineSmall.lineHeight.value * factor).sp
        ),
        titleLarge = BaseTypography.titleLarge.copy(
            fontSize = (BaseTypography.titleLarge.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.titleLarge.lineHeight.value * factor).sp
        ),
        titleMedium = BaseTypography.titleMedium.copy(
            fontSize = (BaseTypography.titleMedium.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.titleMedium.lineHeight.value * factor).sp
        ),
        titleSmall = BaseTypography.titleSmall.copy(
            fontSize = (BaseTypography.titleSmall.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.titleSmall.lineHeight.value * factor).sp
        ),
        bodyLarge = BaseTypography.bodyLarge.copy(
            fontSize = (BaseTypography.bodyLarge.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.bodyLarge.lineHeight.value * factor).sp
        ),
        bodyMedium = BaseTypography.bodyMedium.copy(
            fontSize = (BaseTypography.bodyMedium.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.bodyMedium.lineHeight.value * factor).sp
        ),
        bodySmall = BaseTypography.bodySmall.copy(
            fontSize = (BaseTypography.bodySmall.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.bodySmall.lineHeight.value * factor).sp
        ),
        labelLarge = BaseTypography.labelLarge.copy(
            fontSize = (BaseTypography.labelLarge.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.labelLarge.lineHeight.value * factor).sp
        ),
        labelMedium = BaseTypography.labelMedium.copy(
            fontSize = (BaseTypography.labelMedium.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.labelMedium.lineHeight.value * factor).sp
        ),
        labelSmall = BaseTypography.labelSmall.copy(
            fontSize = (BaseTypography.labelSmall.fontSize.value * factor).sp,
            lineHeight = (BaseTypography.labelSmall.lineHeight.value * factor).sp
        )
    )
}
