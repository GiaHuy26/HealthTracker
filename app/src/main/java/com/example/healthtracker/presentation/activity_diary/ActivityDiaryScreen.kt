package com.example.healthtracker.presentation.activity_diary

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.presentation.components.Cards
import com.example.healthtracker.presentation.activity_diary.component.ActivityCard
import com.example.healthtracker.presentation.theme.Dimens
import com.example.healthtracker.presentation.theme.HealthGreen
import com.example.healthtracker.presentation.theme.HealthOrange
import com.example.healthtracker.presentation.theme.HealthBlue
import com.example.healthtracker.presentation.theme.HealthPurple
import com.example.healthtracker.presentation.theme.HealthTrackerTheme

@Composable
fun ActivityDiaryScreen(
    viewModel: ActivityDiaryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ActivityDiaryContent(
        uiState = uiState,
        onCalendarClick = {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = try { sdf.parse(uiState.selectDate) } catch (e: Exception) { null } ?: Date()
            val cal = Calendar.getInstance().apply { time = date }

            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedCal = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    viewModel.selectDateByMillis(selectedCal.timeInMillis)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        },
        onAddActivity = { type, minutes ->
            viewModel.addActivity(type, minutes)
        },
        onDeleteActivity = { entity ->
            viewModel.deleteActivity(entity)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDiaryContent(
    uiState: ActivityDiaryUiState = ActivityDiaryUiState(),
    onCalendarClick: () -> Unit = {},
    onAddActivity: (ActivityType, Int) -> Unit = { _, _ -> },
    onDeleteActivity: (UserActivityEntity) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showAddDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.activity_diary_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = uiState.displayDate.ifEmpty { stringResource(R.string.food_diary_date_format_pattern) },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outline)
                            .padding(Dimens.SpaceSmall)
                            .clickable {onCalendarClick() }
                    ) {
                        Icon(
                            Icons.Outlined.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
         Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Cards {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.activity_diary_calories_burned_today),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${uiState.totalCalories}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = stringResource(R.string.activity_diary_target_calories_format, uiState.targetCalories),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = Dimens.SpaceExtraSmall)
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = { uiState.progressFloat },
                                modifier = Modifier.size(Dimens.CircularProgressLarge),
                                color = MaterialTheme.colorScheme.secondary,
                                strokeWidth = Dimens.CircularProgressStroke,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                strokeCap = StrokeCap.Round
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${uiState.progressPercentage}%",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
            Cards {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.BgIcon)
                            .clip(CircleShape)
                            .background(HealthOrange.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocalFireDepartment,
                            contentDescription = null,
                            tint = HealthOrange,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                    }
                    Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                    Column {
                        Text(
                            text = stringResource(R.string.activity_diary_stat_calories_burned),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceExtraSmall)
                        ) {
                            Text(
                                text = "${uiState.totalCalories}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = stringResource(R.string.activity_diary_calories_unit),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
            Cards {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.BgIcon)
                            .clip(CircleShape)
                            .background(HealthBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.FormatListBulleted,
                            contentDescription = null,
                            tint = HealthBlue,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                    }
                    Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                    Column {
                        Text(
                            text = stringResource(R.string.activity_diary_stat_activities),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${uiState.activity.size}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
            Cards {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.BgIcon)
                            .clip(CircleShape)
                            .background(HealthPurple.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = null,
                            tint = HealthPurple,
                            modifier = Modifier.size(Dimens.IconNormal)
                        )
                    }
                    Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                    Column {
                        Text(
                            text = stringResource(R.string.activity_diary_stat_exercise_time),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceExtraSmall)
                        ) {
                            Text(
                                text = "${uiState.activity.sumOf { it.durationMinutes }}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = stringResource(R.string.activity_diary_stat_min),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            Text(
                text = stringResource(R.string.activity_diary_todays_activities),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (uiState.activity.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.SpaceLarge),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.activity_diary_empty_list),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                uiState.activity.forEach { entity ->
                    val activityType = try {
                        ActivityType.valueOf(entity.activityType)
                    } catch (e: Exception) {
                        ActivityType.WALKING
                    }
                    ActivityCard(
                        activityType = activityType,
                        durationMinutes = entity.durationMinutes,
                        caloriesBurned = entity.caloriesBurned,
                        onDelete = { onDeleteActivity(entity) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            OutlinedButton(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ButtonHeight),
                shape = RoundedCornerShape(Dimens.CornerExtraLarge),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.IconNormal)
                    )
                    Text(
                        text = stringResource(R.string.activity_diary_add_new_activity),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showAddDialog) {
        var expanded by remember { mutableStateOf(false) }
        var selectedType by remember { mutableStateOf(ActivityType.WALKING) }
        var durationText by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.activity_diary_add_activity),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.activity_diary_select_activity_type),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(Dimens.CornerMedium))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { expanded = true }
                            .padding(Dimens.SpaceMedium)
                    ) {
                        Text(
                            text = stringResource(selectedType.nameResId),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            ActivityType.entries.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(stringResource(type.nameResId)) },
                                    onClick = {
                                        selectedType = type
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = durationText,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() }) {
                                durationText = input
                            }
                        },
                        label = { Text(stringResource(R.string.activity_diary_duration_label)) },
                        placeholder = { Text(stringResource(R.string.activity_diary_duration_placeholder)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val minutes = durationText.toIntOrNull() ?: 0
                        if (minutes > 0) {
                            onAddActivity(selectedType, minutes)
                            showAddDialog = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.btn_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text(stringResource(R.string.food_diary_btn_cancel))
                }
            }
        )
    }
}

@Preview
@Composable
fun ActivityDiaryPreview() {
    HealthTrackerTheme {
        ActivityDiaryContent()
    }
}
