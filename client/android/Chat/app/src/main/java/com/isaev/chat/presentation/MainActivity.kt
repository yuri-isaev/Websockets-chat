package com.isaev.chat.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.isaev.chat.R
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)

    Timer().schedule(3000) {
      startActivity(Intent(applicationContext, RegisterActivity::class.java))
      finish()
    }
  }
}