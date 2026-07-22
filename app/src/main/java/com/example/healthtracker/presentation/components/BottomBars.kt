package com.example.healthtracker.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun BottomBars(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        Icons.Outlined.Home to R.string.bottom_nav_home,
        Icons.Outlined.RestaurantMenu to R.string.bottom_nav_food,
        Icons.Outlined.FitnessCenter to R.string.bottom_nav_workout,
        Icons.Outlined.BarChart to R.string.bottom_nav_stats,
        Icons.Outlined.Settings to R.string.bottom_nav_settings
    )

    Surface(
        modifier = modifier
            .padding(horizontal = Dimens.SpaceLarge, vertical = Dimens.SpaceMedium)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerExtraLarge),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = Dimens.ElevationMedium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.SpaceSmall, horizontal = Dimens.SpaceSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, (icon, labelResId) ->
                val isSelected = selectedTab == index
                val label = stringResource(labelResId)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
                        .background(
                            if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.background
                            }
                        )
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = Dimens.SpaceMedium, vertical = Dimens.SpaceSmall),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                }
            }
        }
    }
}

@Preview(name = "Tab Home Selected", showBackground = true)
@Composable
fun PreviewHomeSelected() {
    BottomBars(selectedTab = 0, onTabSelected = {})
}

@Preview(name = "Tab Settings Selected", showBackground = true)
@Composable
fun PreviewSettingsSelected() {
    BottomBars(selectedTab = 4, onTabSelected = {})
}
