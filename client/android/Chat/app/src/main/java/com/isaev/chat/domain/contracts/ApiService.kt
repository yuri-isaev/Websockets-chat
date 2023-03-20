package com.isaev.chat.domain.contracts

import com.isaev.chat.domain.dto.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

  @POST("Registration")
  fun registerUser(@Body request: RegisterRequest): Call<ServerResponse>

  @POST("/login")
  fun login(@Body requestBody: Map<String, String>): Call<LoginResponse>

  @POST("/saveFCMToken")
  fun saveToken(@Header("Authorization") token: String, @Body requestBody: Map<String, String>): Call<Void>
}