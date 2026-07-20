package com.example.healthtracker.presentation.activity_diary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.presentation.theme.Dimens

@Composable
fun AddActivityDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (ActivityType, Int) -> Unit
) {
    var isActivityMenuExpanded by remember { mutableStateOf(false) }
    var selectedActivityType by remember { mutableStateOf(ActivityType.WALKING) }
    var durationText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
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
                        .clickable { isActivityMenuExpanded = true }
                        .padding(Dimens.SpaceMedium)
                ) {
                    Text(
                        text = stringResource(selectedActivityType.nameResId),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    DropdownMenu(
                        expanded = isActivityMenuExpanded,
                        onDismissRequest = { isActivityMenuExpanded = false },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        ActivityType.entries.forEach { activityType ->
                            DropdownMenuItem(
                                text = { Text(stringResource(activityType.nameResId)) },
                                onClick = {
                                    selectedActivityType = activityType
                                    isActivityMenuExpanded = false
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
                    label = {
                        Text(stringResource(R.string.activity_diary_duration_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.activity_diary_duration_placeholder))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val durationMinutes = durationText.toIntOrNull() ?: 0
                    if (durationMinutes > 0) {
                        onConfirm(selectedActivityType, durationMinutes)
                    }
                }
            ) {
                Text(stringResource(R.string.btn_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.food_diary_btn_cancel))
            }
        }
    )
}
