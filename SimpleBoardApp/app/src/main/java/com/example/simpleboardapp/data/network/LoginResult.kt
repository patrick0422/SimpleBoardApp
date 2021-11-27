package com.example.simpleboardapp.data.network

sealed class LoginResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): LoginResult<T>(data)

    class Error<T>(message: String?, data: T? = null): LoginResult<T>(data, message)

    class Loading<T>: LoginResult<T>()
}
