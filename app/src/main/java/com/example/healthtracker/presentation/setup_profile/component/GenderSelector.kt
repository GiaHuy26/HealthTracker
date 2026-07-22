package com.example.healthtracker.presentation.setup_profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun GenderSelector(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit = {},
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
        Gender.entries.forEach { gender ->
            val isSelected = selectedGender == gender

            val contentColor =
                if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
            val itemBgColor = if (isSelected) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }

            Box(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
                    .background(color = itemBgColor)
                    .clickable { onGenderSelected(gender) },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        if (gender == Gender.MALE) {
                            Icons.Outlined.Male
                        } else {
                            Icons.Outlined.Female
                        },
                        contentDescription = null,
                        tint = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(id = gender.titleResId),
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor
                    )
                }
            }
        }
    }
}

