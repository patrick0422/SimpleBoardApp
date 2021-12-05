package com.example.simpleboardapp.ui.user.register

import android.app.Application
import androidx.lifecycle.*
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.user.UserDataSource
import com.example.simpleboardapp.data.user.RegisterRequest
import com.example.simpleboardapp.data.user.User
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    application: Application,
    private val userDataSource: UserDataSource
): AndroidViewModel(application) {

    private val _registerResponse: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val registerResponse: LiveData<NetworkResult<User>>
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
}