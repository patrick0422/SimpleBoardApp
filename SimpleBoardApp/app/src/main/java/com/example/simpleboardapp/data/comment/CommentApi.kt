package com.example.simpleboardapp.data.comment

import retrofit2.Response
import retrofit2.http.*

interface CommentApi {
    @POST("/comment/{id}")
    suspend fun addComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body commentRequest: CommentRequest
    ): Response<Any>

    @PATCH("/comment/{id}")
    suspend fun editComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body commentRequest: CommentRequest
    ): Response<Any>

    @DELETE("/comment/{id}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Any>
}