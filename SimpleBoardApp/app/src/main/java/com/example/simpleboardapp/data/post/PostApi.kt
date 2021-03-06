package com.example.simpleboardapp.data.post

import retrofit2.Response
import retrofit2.http.*

interface PostApi {
    @GET("/post")
    suspend fun getPosts(@Query("page") page: Int, @Query("k") keyword: String): Response<List<Post>>

    @GET("/post/{id}")
    suspend fun getPost(@Path(value = "id") id: Int): Response<Post>

    @PATCH("/post/{id}")
    suspend fun editPost(@Header("Authorization") token: String, @Path(value = "id") id: Int, @Body post: PostRequest): Response<Post>

    @DELETE("/post/{id}")
    suspend fun deletePost(@Header("Authorization") token: String, @Path(value = "id") id: Int): Response<List<Post>>

    @POST("/post")
    suspend fun addPost(@Header("Authorization") token: String, @Body post: PostRequest): Response<Post>
}