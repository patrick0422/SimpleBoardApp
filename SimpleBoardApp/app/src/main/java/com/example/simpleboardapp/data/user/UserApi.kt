package com.example.simpleboardapp.data.user

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/login")
    suspend fun login(
        @Body
        body: LoginRequest
    ): Response<User>

    @POST("/user/regist")
    suspend fun register(
        @Body
        body: RegisterRequest
    ): Response<User>
}