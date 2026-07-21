package com.example.healthtracker.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    private val _backStack = MutableStateFlow<List<AppNavKey>>(listOf(StartRoute))
    val backStack: StateFlow<List<AppNavKey>> = _backStack.asStateFlow()

    fun navigateTo(route: AppNavKey) {
        val current = _backStack.value.toMutableList()
        current.add(route)
        _backStack.value = current
    }

    fun navigateBack(): Boolean {
        val current = _backStack.value.toMutableList()
        if (current.size > 1) {
            current.removeAt(current.size - 1)
            _backStack.value = current
            return true
        }
        return false
    }

    fun navigateAndClearStack(route: AppNavKey) {
        _backStack.value = listOf(route)
    }

}
