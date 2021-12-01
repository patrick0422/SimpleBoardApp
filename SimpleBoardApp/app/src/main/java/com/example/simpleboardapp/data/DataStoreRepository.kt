package com.example.simpleboardapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.simpleboardapp.util.Constants.Companion.PREFERENCES_NAME
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
        val userToken = stringPreferencesKey(name = PREFERENCES_USER_TOKEN)
    }

    val getUserToken: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            preferences[PreferenceKeys.userToken] ?: ""
        }

    suspend fun saveUserToken(userToken: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.userToken] = userToken
        }
    }
}