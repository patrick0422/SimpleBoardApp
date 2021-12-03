package com.example.simpleboardapp.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.comment.CommentDataSource
import com.example.simpleboardapp.data.comment.CommentRequest
import com.example.simpleboardapp.data.post.Post
import com.example.simpleboardapp.data.post.PostDataSource
import com.example.simpleboardapp.data.post.PostRequest
import com.example.simpleboardapp.data.post.SearchRequest
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val postDataSource: PostDataSource,
    private val commentDataSource: CommentDataSource,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {
    private val _getPostsResponse: MutableLiveData<NetworkResult<List<Post>>> =
        MutableLiveData(NetworkResult.Loading())
    val getPostsResponse: LiveData<NetworkResult<List<Post>>>
        get() = _getPostsResponse

    private val _getPostResponse: MutableLiveData<NetworkResult<Post>> =
        MutableLiveData(NetworkResult.Loading())
    val getPostResponse: LiveData<NetworkResult<Post>>
        get() = _getPostResponse

    private val _addPostResponse: MutableLiveData<NetworkResult<Post>> =
        MutableLiveData(NetworkResult.Loading())
    val addPostResponse: LiveData<NetworkResult<Post>>
        get() = _addPostResponse

    private val _editPostResponse: MutableLiveData<NetworkResult<Post>> =
        MutableLiveData(NetworkResult.Loading())
    val editPostResponse: LiveData<NetworkResult<Post>>
        get() = _editPostResponse

    private val _deletePostResponse: MutableLiveData<NetworkResult<Post>> =
        MutableLiveData(NetworkResult.Loading())
    val deletePostResponse: LiveData<NetworkResult<Post>>
        get() = _deletePostResponse

    private val _addCommentResponse: MutableLiveData<NetworkResult<Any>> =
        MutableLiveData(NetworkResult.Loading())
    val addCommentResponse: LiveData<NetworkResult<Any>>
        get() = _addCommentResponse

    private val _editCommentResponse: MutableLiveData<NetworkResult<Any>> =
        MutableLiveData(NetworkResult.Loading())
    val editCommentResponse: LiveData<NetworkResult<Any>>
        get() = _editCommentResponse

    private val _deleteCommentResponse: MutableLiveData<NetworkResult<Any>> =
        MutableLiveData(NetworkResult.Loading())
    val deleteCommentResponse: LiveData<NetworkResult<Any>>
        get() = _deleteCommentResponse


    fun getPosts(searchRequest: SearchRequest) = viewModelScope.launch {
        delay(1000)
        _getPostsResponse.value = try {
            val response = postDataSource.getPosts(searchRequest)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun getPost(postId: Int) = viewModelScope.launch {
        _getPostResponse.value = try {
            val response = postDataSource.getPost(postId)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun addPost(post: PostRequest) = viewModelScope.launch {
        _addPostResponse.value = try {
            val response = postDataSource.addPost(userToken.value!!, post)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun editPost(postId: Int, post: PostRequest) = viewModelScope.launch {
        _editPostResponse.value = try {
            val response = postDataSource.editPost(userToken.value!!, postId, post)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun deletePost(postId: Int) = viewModelScope.launch {
        _deletePostResponse.value = try {
            val response = postDataSource.deletePost(userToken.value!!, postId)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun addComment(postId: Int, content: String) = viewModelScope.launch {
        _addCommentResponse.value = try {
            val response = commentDataSource.addComment(userToken.value!!, postId, CommentRequest(content))
            if (response.isSuccessful) {
                NetworkResult.Success(response.message())
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun editComment(commentId: Int, content: String) = viewModelScope.launch {
        _editCommentResponse.value = try {
            val response = commentDataSource.editComment(userToken.value!!, commentId, CommentRequest(content))
            if (response.isSuccessful)
                NetworkResult.Success(response.message())
            else
                NetworkResult.Error(response.message())
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun deleteComment(commentId: Int) = viewModelScope.launch {
        _deleteCommentResponse.value = try {
            val response = commentDataSource.deleteComment(userToken.value!!, commentId)
            if (response.isSuccessful)
                NetworkResult.Success(response.message())
            else
                NetworkResult.Error(response.message())
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }


    /** DataStore */
    private val _userToken: MutableLiveData<String> = MutableLiveData()
    val userToken: LiveData<String>
        get() = _userToken

    fun getUserToken() = viewModelScope.launch {
        dataStoreRepository.getUserToken.collect { value ->
            _userToken.value = value
        }
    }

    fun saveUserToken() = viewModelScope.launch {
        dataStoreRepository.saveUserToken(userToken.value ?: "")
    }

    fun deleteUserToken() = viewModelScope.launch {
        dataStoreRepository.saveUserToken("")
    }
}