package com.example.simpleboardapp.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val dataStoreRepository: DataStoreRepository
): ViewModel() {
    fun saveUser(user: User) = viewModelScope.launch {
        dataStoreRepository.saveUser(user)
    }
}