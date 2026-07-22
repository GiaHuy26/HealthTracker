package com.example.healthtracker.presentation.settings.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.domain.model.AppTheme
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun ThemeOption(
    modifier: Modifier,
    icon: ImageVector,
    theme: AppTheme,
    selectedTheme: AppTheme,
    onClick: (AppTheme) -> Unit
) {
    val isSelected = theme == selectedTheme

    Column(
        modifier = modifier
            .border(
                width = Dimens.BorderStrokeSmall,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = RoundedCornerShape(Dimens.CornerLarge)
            )
            .clickable { onClick(theme) }
            .padding(vertical = Dimens.SpaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        Text(
            text = stringResource(theme.titleResId),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
