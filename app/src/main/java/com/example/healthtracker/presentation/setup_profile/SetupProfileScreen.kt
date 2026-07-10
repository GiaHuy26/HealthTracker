package com.example.healthtracker.presentation.setup_profile

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlignVerticalBottom
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.components.TextFields
import com.example.healthtracker.presentation.setup_profile.component.ActivityLevelSelector
import com.example.healthtracker.presentation.setup_profile.component.GenderSelector
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import org.w3c.dom.Text

@Composable
fun SetupProfileScreen() {
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
    onContinueClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
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
                Text(
                    text = stringResource(R.string.label_name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextFields(
                    value = uiState.userName,
                    onChangeValue = onUserNameChange,
                    placeholder = stringResource(R.string.placeholder_name),
                    leadingIcon = Icons.Outlined.Person,
                )
                Text(
                    text = stringResource(R.string.label_birthday),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextFields(
                    value = uiState.birthDate,
                    onChangeValue = onBirthDateChange,
                    placeholder = stringResource(R.string.placeholder_date_format),
                    leadingIcon = Icons.Outlined.CalendarMonth
                )
                Text(
                    text = stringResource(R.string.age_value),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(R.string.label_gender),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                GenderSelector(
                    selectedGender = uiState.gender,
                    onGenderSelected = onGenderClick
                )
            }
        }
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
                        TextFields(
                            value = uiState.weight,
                            onChangeValue = onWeightChange,
                            placeholder = stringResource(R.string.weight),
                            leadingIcon = Icons.Outlined.Scale
                        )
                    }
                    Spacer(Modifier.width(Dimens.SpaceSmall))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.label_height),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextFields(
                            value = uiState.height,
                            onChangeValue = onHeightChange,
                            placeholder = stringResource(R.string.height),
                            leadingIcon = Icons.Outlined.Straighten
                        )
                    }
                }
            }
        }
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
                ActivityLevelSelector(
                    selectedLevel = uiState.activityLevel,
                    onSelectedLevel = onActivityLevelClick
                )
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