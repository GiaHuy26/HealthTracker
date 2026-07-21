package com.example.healthtracker.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.presentation.dashboard.component.CaloriesGoalCard
import com.example.healthtracker.presentation.dashboard.component.CaloriesSummaryCards
import com.example.healthtracker.presentation.dashboard.component.TodayMealsCard
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onAddMealClick: () -> Unit = {},
    onAddActivityClick: () -> Unit = {},
    onViewAllMealsClick: () -> Unit = onAddMealClick
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardContent(
        uiState = uiState,
        onAddMealClick = onAddMealClick,
        onAddActivityClick = onAddActivityClick,
        onViewAllMealsClick = onViewAllMealsClick
    )
}

@Composable
fun DashboardContent(
    uiState: DashboardUiState = DashboardUiState(),
    onAddMealClick: () -> Unit = {},
    onAddActivityClick: () -> Unit = {},
    onViewAllMealsClick: () -> Unit = onAddMealClick
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))

            val displayName = uiState.userName.ifBlank {
                stringResource(R.string.dashboard_default_user_name)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.dashboard_greeting),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpaceExtraSmall))
                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpaceExtraSmall))
                    Text(
                        text = formatDashboardDate(uiState.selectDate),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .size(Dimens.ButtonHeight)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(
                            R.string.dashboard_profile_picture
                        ),
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(Dimens.IconLarge)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            CaloriesGoalCard(uiState)

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            CaloriesSummaryCards(uiState)

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            Text(
                text = when {
                    uiState.remainingCalories > 0 -> stringResource(
                        R.string.dashboard_advice_remaining,
                        uiState.remainingCalories
                    )
                    uiState.remainingCalories < 0 -> stringResource(
                        R.string.dashboard_advice_exceeded,
                        -uiState.remainingCalories
                    )
                    else -> stringResource(R.string.dashboard_advice_enough)
                },
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
            ) {
                Button(
                    onClick = onAddMealClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.ButtonHeightMedium),
                    shape = RoundedCornerShape(Dimens.CornerLarge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    contentPadding = PaddingValues(horizontal = Dimens.SpaceSmall)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                    Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(R.string.dashboard_add_meal),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }

                OutlinedButton(
                    onClick = onAddActivityClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.ButtonHeightMedium),
                    shape = RoundedCornerShape(Dimens.CornerLarge),
                    border = BorderStroke(
                        Dimens.BorderStrokeSmall,
                        MaterialTheme.colorScheme.secondary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    contentPadding = PaddingValues(horizontal = Dimens.SpaceSmall)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.DirectionsRun,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                    Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(R.string.dashboard_add_activity),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            TodayMealsCard(
                meals = uiState.meals,
                onViewAllClick = onViewAllMealsClick
            )
            Spacer(modifier = Modifier.height(Dimens.DashboardBottomContentPadding))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun formatDashboardDate(dateString: String): String {
    val configuration = LocalConfiguration.current
    val pattern = stringResource(R.string.dashboard_date_format_pattern)
    val locale = configuration.locales[0]

    return remember(dateString, pattern, locale) {
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
            isLenient = false
        }
        val displayDateFormat = SimpleDateFormat(pattern, locale)
        val date = runCatching {
            databaseDateFormat.parse(dateString)
        }.getOrNull() ?: Date()

        displayDateFormat.format(date)
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    HealthTrackerTheme {
        DashboardContent(
            uiState = DashboardUiState(isLoading = false)
        )
    }
}
