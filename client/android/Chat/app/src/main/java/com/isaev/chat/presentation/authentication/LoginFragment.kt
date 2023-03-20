package com.isaev.chat.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.isaev.chat.R
import com.isaev.chat.utils.Toaster

class LoginFragment : Fragment() {

  private lateinit var etPhone: EditText
  private lateinit var etPassword: EditText
  private lateinit var btnLogin: Button
  private lateinit var layoutRegister: RelativeLayout
  private val loginViewModel: LoginViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_login, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    etPhone = view.findViewById(R.id.etPhone)
    etPassword = view.findViewById(R.id.etPassword)
    btnLogin = view.findViewById(R.id.btnLogin)
    layoutRegister = view.findViewById(R.id.layoutRegister)

    layoutRegister.setOnClickListener {
      findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    btnLogin.setOnClickListener {
      btnLogin.isEnabled = false
      loginViewModel.login(etPhone.text.toString(), etPassword.text.toString(), requireContext())
    }

    loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { loginResponse ->
      if (loginResponse != null) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
          if (!task.isSuccessful) {
            Log.i("Log", "Fetching FCM registration token failed", task.exception)
            return@addOnCompleteListener
          }

          val token = task.result
          loginViewModel.saveToken(token, requireContext())

//          if (loginResponse.user.image.isEmpty()) {
//            findNavController().navigate(R.id.action_loginFragment_to_myAccountFragment)
//          } else {
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//          }
        }
      }
    })

    loginViewModel.error.observe(viewLifecycleOwner, Observer { error ->
      if (error != null) {
        btnLogin.isEnabled = true
        Toaster.showAlert(requireContext(), "Error", error)
      }
    })
  }
}
