package com.example.simpleboardapp.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleboardapp.SimpleBoardApplication
import com.example.simpleboardapp.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val application: SimpleBoardApplication,
): ViewModel() {
    fun saveUserToken(userToken: String) = viewModelScope.launch {
        application.dataStoreRepository.saveUserToken(userToken)
    }
}