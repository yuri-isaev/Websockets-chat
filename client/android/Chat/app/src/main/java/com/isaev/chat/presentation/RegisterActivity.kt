package com.isaev.chat.presentation

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.isaev.chat.R
import com.isaev.chat.domain.contracts.ApiService
import com.isaev.chat.domain.models.*
import com.isaev.chat.domain.providers.RetrofitClient
import com.isaev.chat.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

  private lateinit var name: EditText
  private lateinit var phone: EditText
  private lateinit var password: EditText
  private lateinit var register: Button
  private lateinit var apiUrlPreference: ApiUrlPreference

  @Suppress("DEPRECATION")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
    setContentView(R.layout.activity_register)

    apiUrlPreference = ApiUrlPreference()
    name = findViewById(R.id.name)
    phone = findViewById(R.id.phone)
    password = findViewById(R.id.password)
    register = findViewById(R.id.register)

    register.setOnClickListener {
      register.isEnabled = false

      val apiService = RetrofitClient
        .getClient(apiUrlPreference.getUrl(this))
        .create(ApiService::class.java)

      val credentials = RegisterRequest(
        name = name.text.toString(),
        phone = phone.text.toString(),
        password = password.text.toString()
      )

      apiService.registerUser(credentials).enqueue(object : Callback<ServerResponse> {
        override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
          register.isEnabled = true

          if (response.isSuccessful) {
            val successResponse = response.body()

            if (successResponse?.status == "success") {
              Utility.showAlert(this@RegisterActivity, "Registered", successResponse.message)
            } else {
              Utility.showAlert(this@RegisterActivity, "Error", successResponse?.message ?: "Unknown error")
            }
          } else {
            Utility.showAlert(this@RegisterActivity, "Error", "Failed to register")
          }
        }

        override fun onFailure(call: Call<ServerResponse>, th: Throwable) {
          register.isEnabled = true
          Log.i("Log", "error = " + th.message)
          Utility.showAlert(this@RegisterActivity, "Error", "Network error")
        }
      })
    }
  }
}