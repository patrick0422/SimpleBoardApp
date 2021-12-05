package com.example.simpleboardapp.ui.user.login

import android.util.Log
import androidx.lifecycle.*
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.LoginRequest
import com.example.simpleboardapp.data.user.User
import com.example.simpleboardapp.util.Constants
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataSource: UserDataSource
): ViewModel() {
    private val _loginResponse: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val loginResponse: LiveData<NetworkResult<User>>
        get() = _loginResponse


    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginResponse.value = NetworkResult.Loading()

        _loginResponse.value = try {
            val response = userDataSource.login(loginRequest)

            if (response.isSuccessful && response.body() != null)
                NetworkResult.Success(response.body()!!)
            else
                NetworkResult.Error(response.message())

        } catch (e: Exception) {
            Log.d(Constants.TAG, "login: ${e.stackTraceToString()}")
            NetworkResult.Error(e.stackTraceToString())
        }
    }
}