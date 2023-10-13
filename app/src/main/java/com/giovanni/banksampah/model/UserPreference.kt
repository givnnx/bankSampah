package com.giovanni.banksampah.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val datastore: DataStore<Preferences>) {

    fun gettingUser(): Flow<UserModel> {
        return datastore.data.map {preferences ->
            UserModel(
                preferences[UID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[LEVEL_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        datastore.edit { preferences ->
            preferences[UID_KEY] = user.uid
            preferences[NAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[LEVEL_KEY] = user.level
            preferences[STATE_KEY] = user.loginState
        }
    }
    suspend fun login() {
        datastore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        datastore.edit { preferences ->
            preferences[STATE_KEY] = false
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val LEVEL_KEY = stringPreferencesKey("level")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}