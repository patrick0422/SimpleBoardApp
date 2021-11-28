package com.example.simpleboardapp.data.post

import retrofit2.Response
import retrofit2.http.*

interface PostApi {
    @GET("/post/{id}")
    suspend fun getPost(@Path(value = "id") id: Int): Response<Post>

    @PATCH("/post/{id}")
    suspend fun editPost(@Path(value = "id") id: Int): Response<PostResponse>

    @DELETE("/post/{id}")
    suspend fun deletePost(@Path(value = "id") id: Int): Response<PostResponse>

    @POST("/post")
    suspend fun uploadPost(@Body post: Post): Response<PostResponse>

    @DELETE("/post")
    suspend fun getPosts(): Response<List<Post>>
}