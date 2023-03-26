package com.isaev.chat.fragments

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.*
import com.isaev.chat.domain.contracts.AuthService
import com.isaev.chat.domain.dto.ServerResponse
import com.isaev.chat.presentation.authentication.*
import com.isaev.chat.utils.ToasterWrapper
import org.junit.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

@RunWith(MockitoJUnitRunner::class)
class RegisterFragmentTest {

  private lateinit var viewModel: RegisterViewModel
  private val authService: AuthService = mock(AuthService::class.java)
  private var toaster: ToasterWrapper = mock(ToasterWrapper::class.java)

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    viewModel = spy(RegisterViewModel(authService))
  }

  @Test
  fun it_should_show_success_message_on_successful_registration() {
    val scenario = launchFragmentInContainer<RegisterFragment>()
    val response = Response.success(ServerResponse("Registered successfully"))

    scenario.onFragment { fragment ->
      val viewModelStore = ViewModelStore()
      ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]

      val registerViewModelProperty = fragment::class.declaredMemberProperties
        .firstOrNull { it.name == "registerViewModel" } as? KMutableProperty<*>
        ?: error("Cannot find mutable property 'registerViewModel'")
      registerViewModelProperty.setter.call(fragment, viewModel)

      // Mock the API response
      val call: Call<ServerResponse> = mock(Call::class.java) as Call<ServerResponse>
      `when`(call.enqueue(any())).then {
        val callback = it.arguments[0] as Callback<ServerResponse>
        callback.onResponse(call, response)
      }
      `when`(authService.registerUser(any())).thenReturn(call)

      // Trigger the registration
      fragment.btnRegister.performClick()

      // Verify the result
      assertEquals("success", viewModel.registerResult.value?.status)
      assertEquals("Registered successfully", viewModel.registerResult.value?.message)
      verify(toaster).showAlert(fragment.requireContext(), "Registered", "Registered successfully")
    }
  }

  @Test
  fun it_should_show_error_message_on_registration_failure() {
    val scenario = launchFragmentInContainer<RegisterFragment>()
    val throwable = Throwable("Failed to register")
    scenario.onFragment { fragment ->
      val viewModelStore = ViewModelStore()
      ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]

      val registerViewModelProperty = fragment::class.declaredMemberProperties
        .firstOrNull { it.name == "registerViewModel" } as? KMutableProperty<*>
        ?: error("Cannot find mutable property 'registerViewModel'")
      registerViewModelProperty.setter.call(fragment, viewModel)

      // Mock the API response
      val call = mock(Call::class.java) as Call<ServerResponse>
      `when`(call.enqueue(any())).then {
        val callback = it.arguments[0] as Callback<ServerResponse>
        callback.onFailure(call, throwable)
      }
      `when`(authService.registerUser(any())).thenReturn(call)

      // Trigger the registration
      fragment.btnRegister.performClick()
      verify(toaster).showAlert(fragment.requireContext(), "Error", "Network error: ${throwable.message}")
    }
  }
}

