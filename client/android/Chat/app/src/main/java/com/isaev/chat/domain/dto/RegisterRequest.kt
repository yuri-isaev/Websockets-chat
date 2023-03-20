package com.isaev.chat.domain.dto

data class RegisterRequest(
  val userName: String,
  val phone: String,
  val password: String
)
