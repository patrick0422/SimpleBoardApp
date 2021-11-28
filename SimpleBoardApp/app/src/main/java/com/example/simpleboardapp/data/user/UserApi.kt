package com.example.simpleboardapp.data.user

import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.data.user.login.LoginResponse
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.data.user.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/login")
    suspend fun login(
        @Body
        body: LoginRequest
    ): Response<LoginResponse>

    @POST("/user/regist")
    suspend fun register(
        @Body
        body: RegisterRequest
    ): Response<RegisterResponse>
}