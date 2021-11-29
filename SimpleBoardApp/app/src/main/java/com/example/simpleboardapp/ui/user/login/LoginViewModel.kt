package com.example.simpleboardapp.ui.user.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.data.user.login.LoginResponse
import com.example.simpleboardapp.util.Constants
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    appication: Application,
    private val userDataSource: UserDataSource,
    private val dataStoreRepository: DataStoreRepository
): AndroidViewModel(appication) {
    private val _loginResponse: MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<NetworkResult<LoginResponse>>
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

    fun saveUserToken(userToken: String) = viewModelScope.launch {
        dataStoreRepository.saveUserToken(userToken)
    }
}