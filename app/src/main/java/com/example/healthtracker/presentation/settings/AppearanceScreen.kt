package com.example.healthtracker.presentation.settings

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.AppColor
import com.example.healthtracker.domain.model.AppFontSize
import com.example.healthtracker.domain.model.AppLanguage
import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.model.AppTheme
import com.example.healthtracker.presentation.settings.component.ColorOption
import com.example.healthtracker.presentation.settings.component.FontSizeSelector
import com.example.healthtracker.presentation.settings.component.LanguageOption
import com.example.healthtracker.presentation.settings.component.ThemeOption
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun AppearanceScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val appSettings = viewModel.appSettings.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    AppearanceContent(
        appSettings = appSettings.value,
        onBackClick = onBackClick,
        onThemeSelected = viewModel::setTheme,
        onColorSelected = viewModel::setColor,
        onFontSizeSelected = viewModel::setFontSize,
        onLanguageSelected = { language ->
            viewModel.setLanguage(language) {
                activity?.recreate()
            }
        }
    )
}

@Composable
fun AppearanceContent(
    appSettings: AppSettings,
    onBackClick: () -> Unit,
    onThemeSelected: (AppTheme) -> Unit,
    onColorSelected: (AppColor) -> Unit,
    onFontSizeSelected: (AppFontSize) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.ScreenPadding)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = stringResource(R.string.settings_appearance),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Text(
            text = stringResource(R.string.appearance_theme_mode),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(Dimens.CornerLarge)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SpaceMedium),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
            ) {
                ThemeOption(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.LightMode,
                    theme = AppTheme.LIGHT,
                    selectedTheme = appSettings.theme,
                    onClick = onThemeSelected
                )
                ThemeOption(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.DarkMode,
                    theme = AppTheme.DARK,
                    selectedTheme = appSettings.theme,
                    onClick = onThemeSelected
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Text(
            text = stringResource(R.string.appearance_accent_color),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(Dimens.CornerLarge)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SpaceMedium),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppColor.entries.forEach { color ->
                    ColorOption(
                        appColor = color,
                        isSelected = appSettings.color == color,
                        onClick = { onColorSelected(color) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Text(
            text = stringResource(R.string.appearance_font_size),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(Dimens.CornerLarge)
        ) {
            FontSizeSelector(
                selectedFontSize = appSettings.fontSize,
                onFontSizeSelected = onFontSizeSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SpaceMedium)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        Text(
            text = stringResource(R.string.appearance_languages),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(Dimens.CornerLarge)
        ) {
            Column(modifier = Modifier.padding(Dimens.SpaceSmall)) {
                AppLanguage.entries.forEach { language ->
                    LanguageOption(
                        language = language,
                        isSelected = appSettings.language == language,
                        onClick = { onLanguageSelected(language) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.StatisticsBottomContentPadding))
    }
}

@Preview(showBackground = true)
@Composable
private fun AppearanceContentPreview() {
    HealthTrackerTheme {
        AppearanceContent(
            appSettings = AppSettings(),
            onBackClick = {},
            onThemeSelected = {},
            onColorSelected = {},
            onFontSizeSelected = {},
            onLanguageSelected = {}
        )
    }
}
