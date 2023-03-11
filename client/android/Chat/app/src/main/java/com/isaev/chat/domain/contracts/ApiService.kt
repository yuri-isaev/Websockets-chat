package com.isaev.chat.domain.contracts

import com.isaev.chat.domain.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
  @POST("/register")
  fun registerUser(@Body request: RegisterRequest): Call<ServerResponse>
}