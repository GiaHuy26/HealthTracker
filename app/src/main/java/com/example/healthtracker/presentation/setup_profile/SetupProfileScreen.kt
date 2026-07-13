package com.example.healthtracker.presentation.setup_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlignVerticalBottom
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.navigation.HomeRoute
import com.example.healthtracker.navigation.NavigationManager
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.components.TextFields
import com.example.healthtracker.presentation.setup_profile.component.ActivityLevelSelector
import com.example.healthtracker.presentation.setup_profile.component.GenderSelector
import com.example.healthtracker.presentation.setup_profile.component.GoalSelector
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun SetupProfileScreen(
    navigationManager: NavigationManager,
    viewModel: SetupProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                SetupProfileViewModel.SetupProfileNavigationEvent.NavigateToHome -> {
                    navigationManager.navigateAndClearStack(HomeRoute)
                }
            }
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
        onSaveProfile = viewModel::onSaveProfile
    )
}

@Composable
fun SetupProfileContent(
    uiState: SetupProfileUiState = SetupProfileUiState(),
    onUserNameChange: (String) -> Unit = {},
    onBirthDateChange: (String) -> Unit = {},
    onGenderClick: (Gender) -> Unit = {},
    onWeightChange: (String) -> Unit = {},
    onHeightChange: (String) -> Unit = {},
    onActivityLevelClick: (ActivityLevel) -> Unit = {},
    onGoalTypeClick: (GoalType) -> Unit = {},
    onSaveProfile: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .verticalScroll(scrollState)
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = stringResource(id = R.string.setup_profile_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.secondary
        )
        if (uiState.errorResId != null) {
            Text(
                text = stringResource(id = uiState.errorResId),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            Spacer(Modifier.height(Dimens.SpaceMedium))
        }
        Spacer(Modifier.height(Dimens.SpaceMedium))
        Cards(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.PersonOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                    Spacer(Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(R.string.section_personal_info),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
                Text(
                    text = stringResource(R.string.label_name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(Dimens.SpaceSmall))
                TextFields(
                    value = uiState.userName,
                    onChangeValue = onUserNameChange,
                    placeholder = stringResource(R.string.placeholder_name),
                    leadingIcon = Icons.Outlined.Person,
                )
                if (uiState.errorResId != null) {
                    Text(
                        text = stringResource(id = uiState.errorResId),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                } else {
                    Spacer(Modifier.height(Dimens.SpaceMedium))
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
                Text(
                    text = stringResource(R.string.label_birthday),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(Dimens.SpaceSmall))
                TextFields(
                    value = uiState.birthDate,
                    onChangeValue = onBirthDateChange,
                    placeholder = stringResource(R.string.placeholder_date_format),
                    leadingIcon = Icons.Outlined.CalendarMonth
                )
                Row {
                    Text(
                        text = stringResource(R.string.age_value),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = stringResource(R.string.age_value, uiState.age ?: 0),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
                Text(
                    text = stringResource(R.string.label_gender),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(Dimens.SpaceSmall))
                GenderSelector(
                    selectedGender = uiState.gender,
                    onGenderSelected = onGenderClick
                )
            }
        }
        Spacer(Modifier.height(Dimens.SpaceSmall))
        Cards {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AlignVerticalBottom,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(R.string.section_body_metrics),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.label_weight),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(Dimens.SpaceSmall))
                        TextFields(
                            value = uiState.weight,
                            onChangeValue = onWeightChange,
                            placeholder = stringResource(R.string.weight),
                            leadingIcon = Icons.Outlined.Scale
                        )
                    }
                    if (uiState.errorResId != null) {
                        Text(
                            text = stringResource(id = uiState.errorResId),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Spacer(Modifier.height(Dimens.SpaceMedium))
                    }
                    Spacer(Modifier.width(Dimens.SpaceSmall))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.label_height),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(Dimens.SpaceSmall))
                        TextFields(
                            value = uiState.height,
                            onChangeValue = onHeightChange,
                            placeholder = stringResource(R.string.height),
                            leadingIcon = Icons.Outlined.Straighten
                        )
                    }
                    if (uiState.errorResId != null) {
                        Text(
                            text = stringResource(id = uiState.errorResId),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Spacer(Modifier.height(Dimens.SpaceMedium))
                    }
                }
            }
        }
        Spacer(Modifier.height(Dimens.SpaceSmall))
        Cards {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.fitness),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(R.string.section_body_metrics),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
                ActivityLevelSelector(
                    selectedLevel = uiState.activityLevel,
                    onSelectedLevel = onActivityLevelClick
                )
            }
        }
        Spacer(Modifier.height(Dimens.SpaceSmall))
        Cards {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Flag,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = stringResource(R.string.section_goal),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
                GoalSelector(
                    selectedGoal = uiState.goalType,
                    onGoalSelected = onGoalTypeClick
                )
            }
        }
        Spacer(Modifier.height(Dimens.SpaceMedium))
        Button(
            text = stringResource(R.string.btn_saveProfile),
            onClick = onSaveProfile
        )
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = HealthGreen)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSetupProfile() {
    HealthTrackerTheme() {
        SetupProfileContent()
    }
}