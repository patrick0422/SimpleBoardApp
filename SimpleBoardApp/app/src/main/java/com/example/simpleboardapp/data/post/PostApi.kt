package com.example.simpleboardapp.data.post

import retrofit2.Response
import retrofit2.http.*

interface PostApi {
    @GET("/post/{id}")
    suspend fun getPost(@Path(value = "id") id: Int): Response<Post>

    @PATCH("/post/{id}")
    suspend fun editPost(@Path(value = "id") id: Int, @Body post: PostRequest): Response<Post>

    @DELETE("/post/{id}")
    suspend fun deletePost(@Path(value = "id") id: Int): Response<Post>

    @POST("/post")
    suspend fun addPost(@Body post: PostRequest): Response<Post>

    @DELETE("/post")
    suspend fun getPosts(@Body searchRequest: SearchRequest): Response<List<Post>>
}