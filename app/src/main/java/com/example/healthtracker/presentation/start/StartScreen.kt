package com.example.healthtracker.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.navigation.LoginRoute
import com.example.healthtracker.navigation.NavigationManager
import com.example.healthtracker.navigation.SignUpRoute
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun StartScreen(
    navigationManager: NavigationManager
) {
    StartContent(
        onStartClick = { navigationManager.navigateTo(SignUpRoute) },
        onLoginClick = { navigationManager.navigateTo(LoginRoute) }
    )
}

@Composable
fun StartContent(
    onStartClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val backgroundColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f),
        MaterialTheme.colorScheme.background
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(backgroundColors))
            .safeContentPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.Logo)
                .clip(RoundedCornerShape(Dimens.CornerMedium))
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.start_subtitle),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Image(
            painter = painterResource(R.drawable.start_screen),
            contentDescription = null,
            modifier = Modifier
                .height(Dimens.ImageStart)
                .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
        )

        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Text(
            text = stringResource(R.string.start_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        Text(
            text = stringResource(R.string.start_description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                text = stringResource(R.string.btn_get_started),
                onClick = onStartClick
            )
        }
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.login_prompt_prefix),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(Dimens.SpaceExtraSmall))
            Text(
                text = stringResource(R.string.login_prompt_action),
                modifier = Modifier.clickable(onClick = onLoginClick),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
    }
}
