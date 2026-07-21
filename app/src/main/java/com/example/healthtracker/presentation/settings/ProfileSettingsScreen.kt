package com.example.healthtracker.presentation.settings

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.presentation.setup_profile.SetupProfileContent
import com.example.healthtracker.presentation.setup_profile.SetupProfileUiState
import com.example.healthtracker.presentation.setup_profile.SetupProfileViewModel
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun ProfileSettingsScreen(
    onBackClick: () -> Unit,
    onProfileSaved: () -> Unit,
    viewModel: SetupProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(onBack = onBackClick)

    LaunchedEffect(Unit) {
        viewModel.loadCurrentProfile()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            onProfileSaved()
        }
    }

    SetupProfileContent(
        uiState = uiState.value,
        onUserNameChange = viewModel::onUserNameChange,
        onBirthDateChange = viewModel::onBirthDateChange,
        onGenderClick = viewModel::onGenderChange,
        onWeightChange = viewModel::onWeightChange,
        onHeightChange = viewModel::onHeightChange,
        onActivityLevelClick = viewModel::onActivityLevelChange,
        onGoalTypeClick = viewModel::onGoalChange,
        onSaveProfile = viewModel::onSaveProfile,
        isEditMode = true,
        onBackClick = onBackClick
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileSettingsContentPreview() {
    HealthTrackerTheme {
        SetupProfileContent(
            uiState = SetupProfileUiState(
                userName = "Quan Gia Huy",
                birthDate = "07/21/2003",
                age = 23,
                gender = Gender.MALE,
                weight = "65",
                height = "170",
                activityLevel = ActivityLevel.LIGHTLY_ACTIVE,
                goalType = GoalType.MAINTAIN_WEIGHT
            ),
            isEditMode = true
        )
    }
}
