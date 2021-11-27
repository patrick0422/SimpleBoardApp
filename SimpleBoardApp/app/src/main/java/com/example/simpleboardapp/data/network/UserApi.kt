package com.example.simpleboardapp.data.network

interface UserApi {
    suspend fun login(): LoginResult
}