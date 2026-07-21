package com.example.healthtracker.presentation.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.healthtracker.domain.model.AppColor
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthOrange
import com.example.healthtracker.presentation.theme.HealthPink
import com.example.healthtracker.presentation.theme.HealthPurple
import com.example.healthtracker.presentation.theme.HealthTeal

@Composable
fun ColorOption(
    appColor: AppColor,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = when (appColor) {
        AppColor.GREEN -> HealthGreen
        AppColor.BLUE -> HealthBlue
        AppColor.PURPLE -> HealthPurple
        AppColor.ORANGE -> HealthOrange
        AppColor.PINK -> HealthPink
        AppColor.TEAL -> HealthTeal
    }

    Box(
        modifier = Modifier
            .size(Dimens.AddButtonSize)
            .background(color, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
