package com.franalarza.dragonballfights.utils

import android.content.Context

class DataStore(context: Context) {

    companion object {
        private const val DATA_STORE = "dataStore"
        const val TOKEN_KEY = "tokenKey"
    }
    private val storage = context.getSharedPreferences(DATA_STORE, Context.MODE_PRIVATE)


    fun savePassword(user: String, pass: String) = storage.edit().putString(user, pass).apply()
    fun readPassword(user: String): String? = storage.getString(user, "")

}
