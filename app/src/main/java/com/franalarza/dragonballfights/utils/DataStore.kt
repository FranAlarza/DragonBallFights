package com.franalarza.dragonballfights.utils

import android.content.Context

class DataStore(context: Context) {

    private val TOKENS = "Token"
    private val USER_NAME = "username"
    private val SAVE_PASS = "savepass"
    private val storage = context.getSharedPreferences(TOKENS, Context.MODE_PRIVATE)


    fun saveIfRememberPasswordChecked(user: String, isChecked: Boolean) = storage.edit().putBoolean(user, isChecked).apply()

    fun savePassword(user: String, pass: String) = storage.edit().putString(user, pass).apply()

    fun readIfRememberPasswordChecked(user: String): Boolean = storage.getBoolean(user, false)
}