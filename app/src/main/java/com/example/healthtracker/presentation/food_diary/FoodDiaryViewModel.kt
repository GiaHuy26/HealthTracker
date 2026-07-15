package com.example.healthtracker.presentation.food_diary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodDiaryViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FoodDiaryUiState())
    val uiState: StateFlow<FoodDiaryUiState> = _uiState.asStateFlow()
}