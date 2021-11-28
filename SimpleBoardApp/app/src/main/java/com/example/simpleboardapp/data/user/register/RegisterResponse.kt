package com.example.simpleboardapp.data.user.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("createdAt")
    val createdAt: String
)