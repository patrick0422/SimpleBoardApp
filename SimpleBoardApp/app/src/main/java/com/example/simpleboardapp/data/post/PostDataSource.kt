package com.example.simpleboardapp.data.post

import retrofit2.Response
import javax.inject.Inject

class PostDataSource @Inject constructor(
    private val postApi: PostApi
) {
    suspend fun getPosts(searchRequest: SearchRequest): Response<List<Post>> = postApi.getPosts(searchRequest)

    suspend fun getPost(id: Int): Response<Post> = postApi.getPost(id)

    suspend fun addPost(post: PostRequest) = postApi.addPost(post)

    suspend fun editPost(id: Int, post: PostRequest): Response<Post> = postApi.editPost(id, post)

    suspend fun deletePost(id: Int): Response<Post> = postApi.deletePost(id)
}