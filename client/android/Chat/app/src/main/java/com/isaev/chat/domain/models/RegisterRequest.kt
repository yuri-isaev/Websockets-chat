package com.isaev.chat.domain.models

data class RegisterRequest(
  val name: String,
  val phone: String,
  val password: String
)
