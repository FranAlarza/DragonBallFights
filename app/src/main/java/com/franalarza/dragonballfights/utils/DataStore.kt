package com.franalarza.dragonballfights.utils

import android.content.Context

class DataStore(context: Context) {

    private val TOKENS = "Token"
    private val storage = context.getSharedPreferences(TOKENS, Context.MODE_PRIVATE)

    fun savePassword(user: String, pass: String) = storage.edit().putString(user, pass).apply()

    fun readPassword(user: String): String? = storage.getString(user, "")
}