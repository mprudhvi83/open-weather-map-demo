package com.open.weather.map.repo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherMapPreference @Inject constructor(@ApplicationContext context: Context) {
    val PREF_NAME = "OpenWeatherMap"
    val LAST_SEARCH = "LastSearch"

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    var lastSearch: String = sharedPreferences.getString(LAST_SEARCH, "").toString()
    fun saveRecentSearch(search: String) {
        sharedPreferences.edit().putString(LAST_SEARCH, search).apply()
    }

    fun getRecentSearch():String {
        return sharedPreferences.getString(LAST_SEARCH, "").toString()
    }

}