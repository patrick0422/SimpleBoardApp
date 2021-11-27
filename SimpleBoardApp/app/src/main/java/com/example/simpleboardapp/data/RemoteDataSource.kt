package com.example.simpleboardapp.data

import com.example.simpleboardapp.data.network.UserApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun login() {

    }
}