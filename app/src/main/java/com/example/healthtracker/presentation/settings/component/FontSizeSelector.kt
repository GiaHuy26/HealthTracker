package com.example.healthtracker.presentation.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.domain.model.AppFontSize
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun FontSizeSelector(
    selectedFontSize: AppFontSize,
    onFontSizeSelected: (AppFontSize) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.ButtonHeightMedium)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(Dimens.CornerExtraLarge)
            )
            .padding(Dimens.SpaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppFontSize.entries.forEach { fontSize ->
            val isSelected = fontSize == selectedFontSize
            val backgroundColor = if (isSelected) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
                    .background(backgroundColor)
                    .clickable { onFontSizeSelected(fontSize) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(fontSize.titleResId),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}
