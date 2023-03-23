package com.isaev.chat.presentation.authentication

import android.content.Context
import androidx.lifecycle.*
import com.isaev.chat.domain.contracts.AuthService
import com.isaev.chat.domain.dto.LoginResponse
import com.isaev.chat.domain.providers.RetrofitClient
import com.isaev.chat.utils.ApiUrlPreference
import retrofit2.*

class LoginViewModel : ViewModel() {

  private val _loginResponse = MutableLiveData<LoginResponse>()
  private val _error = MutableLiveData<String>()
  private val _preferences = ApiUrlPreference()

  val loginResponse: LiveData<LoginResponse> get() = _loginResponse
  val error: LiveData<String> get() = _error

  fun login(phone: String, password: String, context: Context) {
    val apiService = RetrofitClient.getClient(_preferences.getUrl(context)).create(AuthService::class.java)
    val requestBody = mapOf("phone" to phone, "password" to password)

    apiService.login(requestBody).enqueue(object : Callback<LoginResponse> {

      override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
        if (response.isSuccessful) {
          val loginResponse = response.body()
          if (loginResponse?.status == "success") {
            _preferences.setAccessToken(context, loginResponse.accessToken)
            _loginResponse.value = loginResponse
          } else {
            _error.value = loginResponse?.message
          }
        } else {
          _error.value = response.message()
        }
      }

      override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        _error.value = t.message
      }
    })
  }

  fun saveToken(token: String, context: Context) {
    val apiService = RetrofitClient.getClient(_preferences.getUrl(context)).create(AuthService::class.java)
    val requestBody = mapOf("token" to token)

    apiService
      .saveToken("Bearer " + _preferences.getAccessToken(context), requestBody)
      .enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
          // Handle response if needed
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
          _error.value = t.message
        }
      })
  }
}