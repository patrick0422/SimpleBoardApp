package com.example.simpleboardapp.data.post

import retrofit2.Response
import javax.inject.Inject

class PostDataSource @Inject constructor(
    private val postApi: PostApi
) {
    suspend fun getPosts(searchRequest: SearchRequest): Response<List<Post>> = postApi.getPosts(searchRequest.page, searchRequest.keyword)

    suspend fun getPost(id: Int): Response<Post> = postApi.getPost(id)

    suspend fun addPost(token: String, post: PostRequest) = postApi.addPost(token, post)

    suspend fun editPost(token: String, id: Int, post: PostRequest): Response<Post> = postApi.editPost(token, id, post)

    suspend fun deletePost(token: String, id: Int): Response<List<Post>> = postApi.deletePost(token, id)
}