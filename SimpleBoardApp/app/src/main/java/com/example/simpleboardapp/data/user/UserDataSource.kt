package com.example.simpleboardapp.data.user

import retrofit2.Response
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun login(body: LoginRequest): Response<User> =
        userApi.login(body)

    suspend fun register(body: RegisterRequest): Response<User> =
        userApi.register(body)
}