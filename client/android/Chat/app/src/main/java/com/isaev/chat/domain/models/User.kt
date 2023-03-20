package com.isaev.chat.domain.models

class User {
  var _id: String = ""
  var name: String = ""
  var phone: String = ""
  var image: String = ""
  var contacts: ArrayList<UserContact> = ArrayList()
  var createdAt: Long = 0
}