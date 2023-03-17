package com.isaev.chat.presentation.authentication

import android.content.Context
import androidx.lifecycle.*
import com.isaev.chat.domain.contracts.ApiService
import com.isaev.chat.domain.models.*
import com.isaev.chat.domain.providers.RetrofitClient
import com.isaev.chat.utils.ApiUrlPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

  private val _registerResult = MutableLiveData<ServerResponse>()
  val registerResult: LiveData<ServerResponse> get() = _registerResult

  private val _error = MutableLiveData<String>()
  val error: LiveData<String> get() = _error

  fun registerUser(ctx: Context, name: String, phone: String, password: String, preferences: ApiUrlPreference) {

    val apiService = RetrofitClient.getClient(preferences.getUrl(ctx)).create(ApiService::class.java)
    val credentials = RegisterRequest(userName = name, phone = phone, password = password)

    apiService.registerUser(credentials).enqueue(object : Callback<ServerResponse> {

      override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
        if (response.isSuccessful) {
          _registerResult.value = response.body()
        } else {
          _error.value = "Failed to register"
        }
      }

      override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
        _error.value = "Network error: ${t.message}"
      }
    })
  }
}