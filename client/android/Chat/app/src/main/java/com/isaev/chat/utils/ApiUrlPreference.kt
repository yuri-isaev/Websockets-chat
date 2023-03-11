package com.isaev.chat.utils

import android.content.Context
import android.content.SharedPreferences

class ApiUrlPreference {
  private val fileName: String = "API_URL_PREFERENCE"
  private val key: String = "API_URL"

  fun getUrl(context: Context): String {
    val preference: SharedPreferences = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE)
    return preference.getString(key, "http://192.168.8.100:3000").toString()
  }

  fun setUrl(context: Context, apiUrl: String) {
    val preference: SharedPreferences = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE)
    preference.edit().putString(key, apiUrl).apply()
  }
}