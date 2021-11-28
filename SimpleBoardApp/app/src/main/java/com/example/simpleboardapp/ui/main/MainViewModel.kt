package com.example.simpleboardapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainViewModel: ViewModel() {
    private val _token: MutableLiveData<String?> = MutableLiveData()
    val token: LiveData<String?>
        get() = _token


}