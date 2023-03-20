package com.isaev.chat.domain.dto

import com.isaev.chat.domain.models.User

class LoginResponse {
  var status: String = ""
  var message: String = ""
  var accessToken: String = ""
  var user: User = User()
}