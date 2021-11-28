package com.example.simpleboardapp.ui.user.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.data.user.register.RegisterResult
import com.example.simpleboardapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val user: UserDataSource
): ViewModel() {

    private val _registerResponse: MutableLiveData<RegisterResult> = MutableLiveData()
    val registerResponse: LiveData<RegisterResult>
        get() = _registerResponse


    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        val response = user.register(registerRequest)

        _registerResponse.value = try {
            if (response.isSuccessful && response.body() != null) {
                RegisterResult("Succeed!", response.body())
            } else {
                RegisterResult(response.message(), null)
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, "register: ${e.stackTraceToString()}")
            RegisterResult(e.stackTraceToString(), null)
        }
    }
}