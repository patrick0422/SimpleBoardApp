package com.example.simpleboardapp.ui.user.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.data.user.login.LoginResult
import com.example.simpleboardapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val user: UserDataSource
): ViewModel() {
    
    private val _loginResponse: MutableLiveData<LoginResult> = MutableLiveData()
    val loginResponse: LiveData<LoginResult>
        get() = _loginResponse


    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        val response = user.login(loginRequest)

        _loginResponse.value = try {
            if (response.isSuccessful && response.body() != null) {
                LoginResult("Succeed!", response.body())
            } else {
                LoginResult(response.message(), null)
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, "login: ${e.stackTraceToString()}")
            LoginResult(e.stackTraceToString(), null)
        }
    }
}