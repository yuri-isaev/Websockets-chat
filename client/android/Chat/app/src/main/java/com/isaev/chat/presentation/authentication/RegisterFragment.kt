package com.isaev.chat.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.isaev.chat.R
import com.isaev.chat.utils.*

class RegisterFragment : Fragment() {

  private lateinit var name: EditText
  private lateinit var phone: EditText
  private lateinit var password: EditText
  lateinit var btnRegister: Button
  private lateinit var layoutLogin: RelativeLayout
  private lateinit var apiUrlPreference: ApiUrlPreference
  private val registerViewModel: RegisterViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    apiUrlPreference = ApiUrlPreference()
    name = view.findViewById(R.id.etName)
    phone = view.findViewById(R.id.etPhone)
    password = view.findViewById(R.id.etPassword)
    btnRegister = view.findViewById(R.id.btnRegister)
    layoutLogin = view.findViewById(R.id.layoutLogin)

    layoutLogin.setOnClickListener {
      findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    btnRegister.setOnClickListener {
      btnRegister.isEnabled = false
      registerViewModel.registerUser(
        requireContext(),
        name.text.toString(),
        phone.text.toString(),
        password.text.toString(),
        apiUrlPreference
      )
    }

    registerViewModel.registerResult.observe(viewLifecycleOwner, Observer { response ->
      btnRegister.isEnabled = true
      if (response.status == "success") {
        Toaster.showAlert(requireContext(), "Registered", response.message)
      } else {
        Toaster.showAlert(requireContext(), "Error", response.message ?: "Unknown error")
      }
    })

    registerViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
      btnRegister.isEnabled = true
      Log.e("Log", "error = $errorMessage")
      Toaster.showAlert(requireContext(), "Error", errorMessage)
    })
  }
}