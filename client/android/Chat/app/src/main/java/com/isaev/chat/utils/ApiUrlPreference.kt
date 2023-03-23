package com.isaev.chat.utils

import android.content.Context
import android.content.SharedPreferences
import com.isaev.chat.domain.contracts.ApiUrlPreferenceService

class ApiUrlPreference : ApiUrlPreferenceService {
  private val fileName: String = "API_URL_PREFERENCE"
  private val key: String = "API_URL"

  override fun getUrl(context: Context): String {
    val preference: SharedPreferences = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE)
    return preference.getString(key, "http://192.168.0.104:5206/api/Auth/").toString()
  }

  override fun setUrl(context: Context, apiUrl: String): Unit {
    val preference: SharedPreferences = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE)
    preference.edit().putString(key, apiUrl).apply()
  }

  override fun getAccessToken(context: Context): String {
    val preference: SharedPreferences = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE)
    return preference.getString("accessToken", "").toString()
  }

  override fun setAccessToken(context: Context, token: String): Unit {
    val preference: SharedPreferences = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE)
    preference.edit().putString("accessToken", token).apply()
  }
}