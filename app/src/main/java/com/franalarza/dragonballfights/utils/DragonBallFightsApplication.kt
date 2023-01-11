package com.franalarza.dragonballfights.utils

import android.app.Application
import android.provider.ContactsContract.Data
import com.franalarza.dragonballfights.utils.DragonBallFightsApplication.Companion.preferences
import java.util.prefs.AbstractPreferences

class DragonBallFightsApplication: Application() {

    companion object {
        lateinit var preferences: DataStore
    }

    override fun onCreate() {
        super.onCreate()
        preferences = DataStore(applicationContext)
    }
}