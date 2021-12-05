package com.example.simpleboardapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.simpleboardapp.data.user.User
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_NAME
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_USER_CREATED_AT
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_USER_EMAIL
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_USER_ID
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_USER_NICKNAME
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_USER_PASSWORD
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val userId = intPreferencesKey(PREFERENCES_USER_ID)
        val userNickname = stringPreferencesKey(PREFERENCES_USER_NICKNAME)
        val userEmail = stringPreferencesKey(PREFERENCES_USER_EMAIL)
        val userPassword = stringPreferencesKey(PREFERENCES_USER_PASSWORD)
        val userToken = stringPreferencesKey(PREFERENCES_USER_TOKEN)
        val userCreatedAt = stringPreferencesKey(PREFERENCES_USER_CREATED_AT)
    }

    val getUser: Flow<User> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences -> User(
            preferences[PreferenceKeys.userId] ?: 0,
            preferences[PreferenceKeys.userNickname] ?: "Unknown",
            preferences[PreferenceKeys.userEmail] ?: "Unknown",
            preferences[PreferenceKeys.userPassword] ?: "Unknown",
            preferences[PreferenceKeys.userToken] ?: "Unknown",
            preferences[PreferenceKeys.userCreatedAt] ?: "Unknown"
        )}

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.userId] = user.id
            preferences[PreferenceKeys.userNickname] = user.nickname
            preferences[PreferenceKeys.userEmail] = user.email
            preferences[PreferenceKeys.userPassword] = user.password
            preferences[PreferenceKeys.userToken] = user.token
            preferences[PreferenceKeys.userCreatedAt] = user.createdAt
        }
    }
}