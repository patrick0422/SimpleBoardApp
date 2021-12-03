package com.example.simpleboardapp.data.post

import com.example.simpleboardapp.data.comment.Comment

data class Post(
    val title: String,
    val content: String,
    val tags: String,
    val id: Int,
    val comments: List<Comment>
)