package com.example.healthtracker.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.navigation.LoginRoute
import com.example.healthtracker.navigation.NavigationManager
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.components.TextFields
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthLightBlue
import com.example.healthtracker.presentation.theme.HealthLightGreen
import com.example.healthtracker.presentation.theme.HealthTextLight
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun SignUpScreen(
    navigationManager: NavigationManager,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                SignUpViewModel.SignUpNavigationEvent.NavigateToLogin -> navigationManager.navigateBack()
            }
        }
    }

    SignUpContent(
        uiState = uiState.value,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onSignUp = viewModel::onSignUp,
        onLogin = { navigationManager.navigateTo(LoginRoute) }
    )
}

@Composable
fun SignUpContent(
    uiState: SignUpUiState = SignUpUiState(),
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onSignUp: () -> Unit = {},
    onLogin: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        HealthLightGreen,
                        Color.White,
                        HealthLightBlue,
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .safeContentPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimens.Logo)
                    .clip(RoundedCornerShape(Dimens.CornerLarge))
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            Text(
                text = stringResource(R.string.signup_title),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.SpaceLarge))
            Cards {
                TextFields(
                    value = uiState.email,
                    onChangeValue = onEmailChange,
                    placeholder = stringResource(R.string.email),
                    leadingIcon = Icons.Outlined.Email
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
                TextFields(
                    value = uiState.password,
                    onChangeValue = onPasswordChange,
                    placeholder = stringResource(R.string.password),
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    isPasswordVisible = uiState.isPasswordVisible,
                    onToggleVisibility = onTogglePasswordVisibility
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
                TextFields(
                    value = uiState.confirmPassword,
                    onChangeValue = onConfirmPasswordChange,
                    placeholder = stringResource(R.string.confirm_password),
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    isPasswordVisible = uiState.isPasswordVisible,
                    onToggleVisibility = onTogglePasswordVisibility
                )
                if (uiState.errorResId != null) {
                    Text(
                        text = stringResource(id = uiState.errorResId),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(Modifier.height(Dimens.SpaceSmall))
            }

            Spacer(Modifier.height(Dimens.SpaceExtraLarge))
            Button(
                text = stringResource(R.string.signup),
                onClick = onSignUp
            )
            Spacer(Modifier.height(Dimens.SpaceMedium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.ScreenPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.or),
                    modifier = Modifier.padding(horizontal = Dimens.SpaceMedium),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HorizontalDivider(Modifier.weight(1f))
            }
            Spacer(Modifier.height(Dimens.SpaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = (RoundedCornerShape(Dimens.CornerExtraLarge)),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = Dimens.Elevation
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = HealthTextLight
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.ScreenPadding),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                        Spacer(Modifier.width(Dimens.SpaceSmall))
                        Text(
                            text = stringResource(R.string.google),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = (RoundedCornerShape(Dimens.CornerExtraLarge)),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = Dimens.Elevation
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = HealthTextLight
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.ScreenPadding),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                        Spacer(Modifier.width(Dimens.SpaceSmall))
                        Text(
                            text = stringResource(R.string.facebook),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(Modifier.height(Dimens.SpaceExtraLarge))
            Row {
                Text(
                    text = stringResource(R.string.login_prompt_prefix),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(Dimens.SpaceSmall))
                Text(
                    text = stringResource(R.string.login_prompt_action),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable(onClick = onLogin)
                )
            }
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
fun PreviewSignUp() {
    HealthTrackerTheme() {
        SignUpContent()
    }
}