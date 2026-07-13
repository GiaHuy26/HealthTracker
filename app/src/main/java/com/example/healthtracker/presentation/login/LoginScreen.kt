package com.example.healthtracker.presentation.login

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.navigation.HomeRoute
import com.example.healthtracker.navigation.NavigationManager
import com.example.healthtracker.navigation.SignUpRoute
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.components.TextFields
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthCardLight
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthLightBlue
import com.example.healthtracker.presentation.theme.HealthLightGreen
import com.example.healthtracker.presentation.theme.HealthTextLight
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun LoginScreen(
    navigationManager: NavigationManager,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                LoginViewModel.LoginNavigationEvent.NavigationToHome -> navigationManager.navigateAndClearStack(
                    HomeRoute
                )
            }
        }
    }

    LoginContent(
        uiState = uiState.value,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisible = viewModel::onPasswordVisible,
        onLogIn = viewModel::onLoginClick,
        onSignIn = { navigationManager.navigateTo(SignUpRoute) }
    )
}

@Composable
fun LoginContent(
    uiState: LoginUiState = LoginUiState(),
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLogIn: () -> Unit = {},
    onSignIn: () -> Unit = {},
    onPasswordVisible: () -> Unit = {}
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
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimens.Logo)
                    .clip(RoundedCornerShape(Dimens.CornerLarge))
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            Text(
                text = stringResource(id = R.string.login_welcome),
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
                    onToggleVisibility = onPasswordVisible
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
                    text = stringResource(R.string.login_forgot_password),
                    modifier = Modifier.align(alignment = Alignment.End),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.height(Dimens.SpaceExtraLarge))
            Button(
                onClick = onLogIn,
                text = stringResource(R.string.login_button)
            )
            Spacer(Modifier.height(Dimens.SpaceExtraLarge))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.ScreenPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.or),
                    modifier = Modifier.padding(horizontal = Dimens.SpaceMedium),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(Dimens.SpaceLarge))
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
                    text = stringResource(id = R.string.login_prefix),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(Dimens.SpaceSmall))
                Text(
                    text = stringResource(id = R.string.login_signUp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable(onClick = onSignIn)
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
fun PreviewLogin() {
    HealthTrackerTheme() {
        LoginContent()
    }
}