package com.example.healthtracker.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.healthtracker.di.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.sessionDataStore by preferencesDataStore(name = "session_preferences")

class SessionPreferences @Inject constructor(
    @ApplicationContext context: Context
) : SessionManager {
    private val dataStore = context.sessionDataStore

    override suspend fun saveUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[userEmailKey] = email
        }
    }

    override suspend fun getCurrentUserEmail(): String {
        return dataStore.data.first()[userEmailKey].orEmpty()
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(userEmailKey)
        }
    }

    private companion object {
        val userEmailKey = stringPreferencesKey("user_email")
    }
}
