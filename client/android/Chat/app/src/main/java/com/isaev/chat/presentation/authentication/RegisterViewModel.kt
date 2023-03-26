package com.isaev.chat.presentation.authentication

import android.content.Context
import androidx.lifecycle.*
import com.isaev.chat.domain.contracts.*
import com.isaev.chat.domain.dto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val authService: AuthService) : ViewModel() {

  val registerResult = MutableLiveData<ServerResponse>()
  val error = MutableLiveData<String>()

  fun registerUser(ctx: Context, userName: String, phone: String, password: String, preference: ApiUrlPreferenceService) {
    val credentials = RegisterRequest(userName, phone, password)

    authService.registerUser(credentials).enqueue(object : Callback<ServerResponse> {
      override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
        if (response.isSuccessful) {
          registerResult.value = response.body()
        } else {
          error.value = "Failed to register"
        }
      }

      override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
        error.value = "Network error: ${t.message}"
      }
    })
  }
}
