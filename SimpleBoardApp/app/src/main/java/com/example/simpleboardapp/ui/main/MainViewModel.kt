package com.example.simpleboardapp.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.simpleboardapp.data.DataStoreRepository
import com.example.simpleboardapp.data.post.Post
import com.example.simpleboardapp.data.post.PostDataSource
import com.example.simpleboardapp.data.post.PostRequest
import com.example.simpleboardapp.data.post.SearchRequest
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val postDataSource: PostDataSource,
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

    fun getPosts(searchRequest: SearchRequest) = viewModelScope.launch {
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

    fun getPost(id: Int) = viewModelScope.launch {
        _getPostResponse.value = try {
            val response = postDataSource.getPost(id)

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
            val response = postDataSource.addPost(post)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun editPost(id: Int, post: PostRequest) = viewModelScope.launch {
        _editPostResponse.value = try {
            val response = postDataSource.editPost(id, post)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    fun deletePost(id: Int) = viewModelScope.launch {
        _deletePostResponse.value = try {
            val response = postDataSource.deletePost(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.message())
            }
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
}