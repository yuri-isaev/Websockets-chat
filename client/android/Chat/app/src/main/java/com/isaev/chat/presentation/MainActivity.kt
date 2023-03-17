package com.isaev.chat.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import com.isaev.chat.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

  private var mNavController: NavController? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // The application is displayed in full screen mode without a window title.
    requestWindowFeature(Window.FEATURE_NO_TITLE)

    // Window flags to display the activity in full screen mode, hiding the status bar and other system interface elements.
    window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)

    setContentView(R.layout.activity_main)

    // Initial navigation controller from NavHostFragment and using handler to delay the transition
    Handler(Looper.getMainLooper()).postDelayed({
      mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }, 3000)
  }
}