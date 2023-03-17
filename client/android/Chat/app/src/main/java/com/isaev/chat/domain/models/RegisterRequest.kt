package com.isaev.chat.domain.models

data class RegisterRequest(
  val userName: String,
  val phone: String,
  val password: String
)
