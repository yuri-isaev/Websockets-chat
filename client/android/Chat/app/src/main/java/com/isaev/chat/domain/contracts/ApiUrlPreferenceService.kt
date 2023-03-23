package com.isaev.chat.domain.contracts

import android.content.Context

interface ApiUrlPreferenceService {
  fun getUrl(context: Context): String
  fun setUrl(context: Context, apiUrl: String)
  fun getAccessToken(context: Context): String
  fun setAccessToken(context: Context, token: String)
}