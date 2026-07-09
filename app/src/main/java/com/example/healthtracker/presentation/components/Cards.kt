package com.example.healthtracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTextLight

@Composable
fun Cards(
    modifier: Modifier= Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerExtraLarge),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = Dimens.Elevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = HealthTextLight
        )

    ) {
        Column(modifier = Modifier.padding(Dimens.CardPadding)) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    Cards { }
}