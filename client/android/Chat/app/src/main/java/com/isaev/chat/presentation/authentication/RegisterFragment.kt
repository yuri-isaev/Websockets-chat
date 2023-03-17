package com.isaev.chat.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import com.isaev.chat.R
import com.isaev.chat.utils.*

class RegisterFragment : Fragment() {

  private lateinit var name: EditText
  private lateinit var phone: EditText
  private lateinit var password: EditText
  private lateinit var register: Button
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
    register = view.findViewById(R.id.btnRegister)

    register.setOnClickListener {
      register.isEnabled = false
      registerViewModel.registerUser(
        requireContext(),
        name.text.toString(),
        phone.text.toString(),
        password.text.toString(),
        apiUrlPreference
      )
    }

    registerViewModel.registerResult.observe(viewLifecycleOwner, Observer { response ->
      register.isEnabled = true
      if (response.status == "success") {
        Toaster.showAlert(requireContext(), "Registered", response.message)
      } else {
        Toaster.showAlert(requireContext(), "Error", response.message ?: "Unknown error")
      }
    })

    registerViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
      register.isEnabled = true
      Log.e("Log", "error = $errorMessage")
      Toaster.showAlert(requireContext(), "Error", errorMessage)
    })
  }
}