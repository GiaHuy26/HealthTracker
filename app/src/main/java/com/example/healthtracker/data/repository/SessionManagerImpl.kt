package com.example.healthtracker.data.repository

import android.content.Context
import com.example.healthtracker.di.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import androidx.core.content.edit

class SessionManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : SessionManager {
    private val sharedPrefs =
        context.getSharedPreferences("health_tracker_prefs", Context.MODE_PRIVATE)

    override suspend fun saveUserEmail(email: String) {
        sharedPrefs.edit { putString("KEY_USER_EMAIL", email) }
    }

    override suspend fun getCurrentUserEmail(): String {
        return sharedPrefs.getString("KEY_USER_EMAIL", "") ?: ""
    }

    override suspend fun clearSession() {
        sharedPrefs.edit {
            remove("KEY_USER_EMAIL").apply()
        }
    }
}