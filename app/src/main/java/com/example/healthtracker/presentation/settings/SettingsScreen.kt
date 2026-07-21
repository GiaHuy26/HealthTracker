package com.example.healthtracker.presentation.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.domain.model.SettingsPage
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.R
import com.example.healthtracker.presentation.settings.component.ProfileSummaryCard
import com.example.healthtracker.presentation.settings.component.LogoutButton
import com.example.healthtracker.presentation.settings.component.SettingsItem
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onLogoutComplete: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var currentPage by rememberSaveable { mutableStateOf(SettingsPage.MAIN) }

    BackHandler(enabled = currentPage != SettingsPage.MAIN) {
        currentPage = SettingsPage.MAIN
        viewModel.loadProfile()
    }

    when (currentPage) {
        SettingsPage.MAIN -> SettingsContent(
            uiState = uiState.value,
            onProfileClick = { currentPage = SettingsPage.PROFILE },
            onAppearanceClick = { currentPage = SettingsPage.APPEARANCE },
            onLogoutClick = { viewModel.logout(onLogoutComplete) }
        )

        SettingsPage.PROFILE -> ProfileSettingsScreen(
            onBackClick = {
                currentPage = SettingsPage.MAIN
                viewModel.loadProfile()
            },
            onProfileSaved = {
                currentPage = SettingsPage.MAIN
                viewModel.loadProfile()
            }
        )

        SettingsPage.APPEARANCE -> AppearanceScreen(
            onBackClick = { currentPage = SettingsPage.MAIN }
        )
    }
}

@Composable
fun SettingsContent(
    uiState: SettingsUiState,
    onProfileClick: () -> Unit,
    onAppearanceClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.settings_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            ProfileSummaryCard(profile = uiState.profile)
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.CornerLarge),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(Dimens.Elevation)
            ) {
                SettingsItem(
                    icon = Icons.Outlined.Person,
                    title = stringResource(R.string.settings_profile),
                    subtitle = stringResource(R.string.settings_profile_subtitle),
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClick = onProfileClick
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                SettingsItem(
                    icon = Icons.Outlined.Palette,
                    title = stringResource(R.string.settings_appearance),
                    subtitle = stringResource(R.string.settings_appearance_subtitle),
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClick = onAppearanceClick
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            LogoutButton(onClick = onLogoutClick)

            Spacer(modifier = Modifier.height(Dimens.StatisticsBottomContentPadding))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsContentPreview() {
    HealthTrackerTheme {
        SettingsContent(
            uiState = SettingsUiState(
                profile = Profile(
                    userName = "Quân Gia Huy",
                    birthDate = "07/21/2003",
                    gender = Gender.MALE,
                    weight = 65f,
                    height = 170f,
                    activeLevel = ActivityLevel.LIGHTLY_ACTIVE,
                    goalType = GoalType.MAINTAIN_WEIGHT
                ),
                isLoading = false
            ),
            onProfileClick = {},
            onAppearanceClick = {},
            onLogoutClick = {}
        )
    }
}
