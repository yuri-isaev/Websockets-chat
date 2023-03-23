package com.isaev.chat

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.isaev.chat.domain.contracts.*
import com.isaev.chat.domain.dto.*
import com.isaev.chat.presentation.authentication.RegisterViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.*
import retrofit2.*

class AuthServiceTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var authService: AuthService
  private lateinit var apiPreferenceService: ApiUrlPreferenceService
  private lateinit var viewModel: RegisterViewModel

  @Before
  fun setup() {
    authService = mockk()
    apiPreferenceService = mockk()
    viewModel = RegisterViewModel(authService)
  }

  @Test
  fun it_should_return_success_response() {
    // Given
    val credentials = RegisterRequest("Tester", "89520000000", "gru48gf839fhq2pfHFE49")
    val serverResponse = ServerResponse("success")
    val call: Call<ServerResponse> = mockk()
    val context: Context = mockk()
    val baseUrl = "http://192.168.0.104:5206/api/Auth/"

    every { apiPreferenceService.getUrl(context) } returns baseUrl
    every { authService.registerUser(credentials) } returns call
    every { call.enqueue(any()) } answers {
      val callback = args[0] as Callback<ServerResponse>
      callback.onResponse(call, Response.success(serverResponse))
    }

    val observer = mockk<Observer<ServerResponse>>(relaxed = true)
    viewModel.registerResult.observeForever(observer)

    // When
    viewModel.registerUser(context, credentials.userName, credentials.phone, credentials.password, apiPreferenceService)

    // Then
    verify { observer.onChanged(serverResponse) }
    assertEquals(serverResponse, viewModel.registerResult.value)
  }

  @Test
  fun it_should_return_error_response() {
    // Given
    val credentials = RegisterRequest("Tester", "89520557056", "gru48gf839fhq2pfHFE49")
    val call: Call<ServerResponse> = mockk()
    val context: Context = mockk()
    val baseUrl = "http://192.168.0.104:5206/api/Auth/"
    val responseBody: ResponseBody = mockk()

    every { apiPreferenceService.getUrl(context) } returns baseUrl
    every { authService.registerUser(credentials) } returns call
    every { responseBody.contentType() } returns mockk()
    every { responseBody.contentLength() } returns 0
    every { call.enqueue(any()) } answers {
      val callback = args[0] as Callback<ServerResponse>
      callback.onResponse(call, Response.error(400, responseBody))
    }

    val viewModel = RegisterViewModel(authService)
    val errorObserver = mockk<Observer<String>>(relaxed = true)
    viewModel.error.observeForever(errorObserver)

    // When
    viewModel.registerUser(context, credentials.userName, credentials.phone, credentials.password, apiPreferenceService)

    // Then
    verify { errorObserver.onChanged("Failed to register") }
    assertEquals("Failed to register", viewModel.error.value)
  }

  @Test
  fun it_should_return_network_error() {
    // Given
    val credentials = RegisterRequest("Tester", "89520557056", "gru48gf839fhq2pfHFE49")
    val call: Call<ServerResponse> = mockk()
    val context: Context = mockk()
    val baseUrl = "http://192.168.0.104:5206/api/Auth/"
    val exception = Exception("Network error")

    every { apiPreferenceService.getUrl(context) } returns baseUrl
    every { authService.registerUser(credentials) } returns call
    every { call.enqueue(any()) } answers {
      val callback = args[0] as Callback<ServerResponse>
      callback.onFailure(call, exception)
    }

    val viewModel = RegisterViewModel(authService)
    val errorObserver = mockk<Observer<String>>(relaxed = true)
    viewModel.error.observeForever(errorObserver)

    // When
    viewModel.registerUser(context, credentials.userName, credentials.phone, credentials.password, apiPreferenceService
    )

    // Then
    verify { errorObserver.onChanged("Network error: ${exception.message}") }
    assertEquals("Network error: ${exception.message}", viewModel.error.value)
  }
}
