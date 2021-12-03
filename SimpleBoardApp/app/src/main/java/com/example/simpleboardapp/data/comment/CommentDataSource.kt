package com.example.simpleboardapp.data.comment

import retrofit2.Response
import javax.inject.Inject

class CommentDataSource @Inject constructor(
    private val commentApi: CommentApi
) {
    suspend fun addComment(
        token: String,
        postId: Int,
        commentRequest: CommentRequest
    ): Response<Any> = commentApi.addComment(token, postId, commentRequest)

    suspend fun editComment(
        token: String, commentId: Int, commentRequest: CommentRequest
    ): Response<Any> = commentApi.addComment(token, commentId, commentRequest)

    suspend fun deleteComment(
        token: String, commentId: Int
    ): Response<Any> = commentApi.deleteComment(token, commentId)
}