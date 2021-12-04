package com.example.simpleboardapp.data.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("createdAt")
    val createdAt: String
)

fun getEmptyUser(): User = User(-1, "", "", "", "", "")
