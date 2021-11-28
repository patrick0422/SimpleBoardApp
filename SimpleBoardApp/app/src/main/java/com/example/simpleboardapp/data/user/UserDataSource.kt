package com.example.simpleboardapp.data.user

import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.data.user.login.LoginResponse
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.data.user.register.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun login(body: LoginRequest): Response<LoginResponse> =
        userApi.login(body)

    suspend fun register(body: RegisterRequest): Response<RegisterResponse> =
        userApi.register(body)
}