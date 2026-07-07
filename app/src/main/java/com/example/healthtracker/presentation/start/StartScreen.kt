package com.example.healthtracker.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.presentation.theme.HealthTextDark
import com.example.healthtracker.presentation.theme.HealthTextMutedGreen
import com.example.healthtracker.presentation.components.Button

@Composable
fun StartScreen(
    onStart: () -> Unit = {},
    onLogin: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = R.string.start_subtitle),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Image(
            painter = painterResource(id = R.drawable.start_screen),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.start_title),
            style = MaterialTheme.typography.titleLarge,
            color = HealthTextDark
        )
        Text(
            text = stringResource(id = R.string.start_description),
            style = MaterialTheme.typography.bodySmall,
            color = HealthTextMutedGreen
        )
        Button(
            text = stringResource(id = R.string.btn_get_started),
            onClick = onStart
        )
    }
}

@Preview
@Composable
fun PreviewStartScreen() {
    StartScreen() { }
}

