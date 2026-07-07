package com.example.healthtracker.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.theme.HealthTextDark
import com.example.healthtracker.presentation.theme.HealthTextMutedGreen
import com.example.healthtracker.presentation.components.Button
import com.example.healthtracker.presentation.theme.HealthTrackerTheme
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthLightBlue
import com.example.healthtracker.presentation.theme.HealthLightGreen

@Composable
fun StartScreen(
    onStart: () -> Unit = {},
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
                .fillMaxSize()
                .padding(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimens.Logo)
                    .clip(shape = RoundedCornerShape(percent = 20))
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.SpaceExtraSmall))
            Text(
                text = stringResource(id = R.string.start_subtitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(Dimens.SpaceLarge))
            Image(
                painter = painterResource(id = R.drawable.start_screen),
                contentDescription = null,
                modifier = Modifier
                    .height(Dimens.ImageStart)
                    .clip(RoundedCornerShape(Dimens.CornerExtraLarge))
            )
            Spacer(Modifier.height(Dimens.SpaceLarge))
            Text(
                text = stringResource(id = R.string.start_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.SpaceSmall))
            Text(
                text = stringResource(id = R.string.start_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(Dimens.SpaceExtraLarge))
            Button(
                text = stringResource(id = R.string.btn_get_started),
                onClick = onStart
            )
            Spacer(Modifier.height(Dimens.SpaceExtraLarge))
            Row() {
                Text(
                    text = stringResource(id = R.string.login_prompt_prefix),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(Dimens.SpaceExtraSmall))

                Text(
                    text = stringResource(id = R.string.login_prompt_action),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable(onClick = onLogin)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewStartScreen() {
    HealthTrackerTheme {
        StartScreen()
    }
}

