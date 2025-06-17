package com.emanh.rootapp.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.data.db.entity.UsersEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UserDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    companion object {
        private val USER_ID = longPreferencesKey("user_id")
        private val IS_ARTIST = booleanPreferencesKey("is_artist")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val PASSWORD = stringPreferencesKey("password")
        private val AVATAR = stringPreferencesKey("avatar")
        private val NAME = stringPreferencesKey("name")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    override suspend fun saveUserInfo(user: UsersEntity) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user.userId
            preferences[IS_ARTIST] = user.isArtist
            preferences[USER_NAME] = user.name ?: ""
            preferences[USER_EMAIL] = user.email ?: ""
            preferences[PASSWORD] = user.password ?: ""
            preferences[AVATAR] = user.avatarUrl ?: ""
            preferences[NAME] = user.name ?: ""
            preferences[IS_LOGGED_IN] = true
        }
    }

    override fun getUserInfo(): Flow<UserInfo?> {
        return context.dataStore.data.map { preferences ->
            val isLoggedIn = preferences[IS_LOGGED_IN] == true

            if (isLoggedIn) {
                UserInfo(id = preferences[USER_ID] ?: 0L,
                         isArtist = preferences[IS_ARTIST] == true,
                         name = preferences[USER_NAME] ?: "",
                         email = preferences[USER_EMAIL] ?: "",
                         password = preferences[PASSWORD] ?: "",
                         avatarUrl = preferences[AVATAR] ?: "",
                         username = preferences[NAME] ?: "",
                         isLoggedIn = true)
            } else {
                null
            }
        }
    }

    override suspend fun clearUserInfo() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] == true
        }
    }
}