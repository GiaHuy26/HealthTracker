package com.example.healthtracker.presentation.activity_diary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsBike
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Pool
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.SportsBasketball
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material.icons.outlined.SportsTennis
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

private fun getActivityIcon(type: ActivityType): ImageVector {
    return when (type) {
        ActivityType.WALKING -> Icons.AutoMirrored.Outlined.DirectionsWalk
        ActivityType.RUNNING -> Icons.AutoMirrored.Outlined.DirectionsRun
        ActivityType.CYCLING -> Icons.AutoMirrored.Outlined.DirectionsBike
        ActivityType.SWIMMING -> Icons.Outlined.Pool
        ActivityType.YOGA -> Icons.Outlined.SelfImprovement
        ActivityType.GYM -> Icons.Outlined.FitnessCenter
        ActivityType.SOCCER -> Icons.Outlined.SportsSoccer
        ActivityType.BASKETBALL -> Icons.Outlined.SportsBasketball
        ActivityType.TENNIS, ActivityType.TABLE_TENNIS, ActivityType.BADMINTON -> Icons.Outlined.SportsTennis
        else -> Icons.Outlined.FitnessCenter
    }
}

private fun getIntensityResId(met: Double): Int {
    return when {
        met < 4.0 -> R.string.activity_intensity_relaxed
        met < 7.0 -> R.string.activity_intensity_moderate
        else -> R.string.activity_intensity_intense
    }
}

@Composable
fun ActivityCard(
    activityType: ActivityType,
    durationMinutes: Int,
    caloriesBurned: Int,
    onDelete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Cards(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.BgIcon)
                    .clip(CircleShape)
                    .background(HealthGreen.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getActivityIcon(activityType),
                    contentDescription = null,
                    tint = HealthGreen,
                    modifier = Modifier.size(Dimens.IconNormal)
                )
            }
            Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(activityType.nameResId),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(
                        R.string.activity_diary_duration_intensity_format,
                        durationMinutes,
                        stringResource(getIntensityResId(activityType.met))
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$caloriesBurned",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.activity_diary_calories_unit),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(Dimens.IconNormal)
            )
        }
    }
}

@Preview
@Composable
fun ActivityCardPreview() {
    HealthTrackerTheme {
        ActivityCard(
            activityType = ActivityType.WALKING,
            durationMinutes = 30,
            caloriesBurned = 120
        )
    }
}
