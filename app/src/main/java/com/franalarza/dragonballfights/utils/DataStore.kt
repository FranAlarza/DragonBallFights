package com.franalarza.dragonballfights.utils

import android.content.Context

class DataStore(context: Context) {

    companion object {
        private val DATA_STORE = "dataStore"
        val TOKEN_KEY = "tokenKey"
    }
    private val storage = context.getSharedPreferences(DATA_STORE, Context.MODE_PRIVATE)

    fun saveUser(user: String, pass: String) = storage.edit().putString(user, pass).apply()
    fun readUser(user: String): String? = storage.getString(user, "")

    fun savePassword(user: String, pass: String) = storage.edit().putString(user, pass).apply()
    fun readPassword(user: String): String? = storage.getString(user, "")

    fun saveToken(user: String, token: String) = storage.edit().putString("$user$TOKEN_KEY", token).apply()
    fun readToken(user: String): String? = storage.getString("$user$TOKEN_KEY", "")
}