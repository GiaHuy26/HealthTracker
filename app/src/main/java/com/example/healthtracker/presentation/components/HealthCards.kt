package com.example.healthtracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthGreen

@Composable
fun HealthCards(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(HealthGreen, HealthBlue)
                )
            ),
        shape = RoundedCornerShape(Dimens.CornerExtraLarge),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = Dimens.Elevation
        ),
    ) {
        Column(modifier = Modifier.padding(Dimens.CardPadding)) {
            content()
        }
    }
}