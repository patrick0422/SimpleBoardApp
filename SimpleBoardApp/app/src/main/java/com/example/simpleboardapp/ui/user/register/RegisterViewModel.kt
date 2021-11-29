package com.example.simpleboardapp.ui.user.register

import android.app.Application
import androidx.lifecycle.*
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.data.user.register.RegisterResponse
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    application: Application,
    private val userDataSource: UserDataSource,
    private val dataStoreRepository: DataStoreRepository
): AndroidViewModel(application) {

    private val _registerResponse: MutableLiveData<NetworkResult<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<NetworkResult<RegisterResponse>>
        get() = _registerResponse


    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        _registerResponse.value = NetworkResult.Loading()

        _registerResponse.value = try {
            val response = userDataSource.register(registerRequest)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun saveUserToken(userToken: String) = viewModelScope.launch {
        dataStoreRepository.saveUserToken(userToken)
    }
}