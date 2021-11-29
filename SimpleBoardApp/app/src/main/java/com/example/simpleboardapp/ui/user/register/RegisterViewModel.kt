package com.example.simpleboardapp.ui.user.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.data.user.register.RegisterResponse
import com.example.simpleboardapp.util.Constants
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val user: UserDataSource
): ViewModel() {

    private val _registerResponse: MutableLiveData<NetworkResult<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<NetworkResult<RegisterResponse>>
        get() = _registerResponse


    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        _registerResponse.value = NetworkResult.Loading()

        _registerResponse.value = try {
            val response = user.register(registerRequest)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }
}