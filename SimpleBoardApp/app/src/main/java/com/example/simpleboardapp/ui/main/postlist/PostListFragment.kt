package com.example.simpleboardapp.ui.main.postlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.post.SearchRequest
import com.example.simpleboardapp.databinding.FragmentPostListBinding
import com.example.simpleboardapp.ui.main.MainViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.Constants.Companion.TAG
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostListFragment : BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {
    private val mainViewModel: MainViewModel by viewModels()
    private val postListViewModel: PostListViewModel by viewModels()
    private val mAdapter: PostListAdapter by lazy { PostListAdapter() }

    override fun init() {
        binding.postView.adapter = mAdapter

        binding.fabAddPost.setOnClickListener {
            it.findNavController().navigate(R.id.action_postListFragment_to_uploadFragment)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            getPosts()
        }

        if (mAdapter.postList.isEmpty())
            getPosts()
    }

    override fun onResume() {
        super.onResume()

        getPosts()
    }

    private fun getPosts() {
        mainViewModel.getPosts(SearchRequest(1, ""))

        mainViewModel.getPostsResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mAdapter.setData(response.data!!.asReversed()) // 최신 글 순으로
                    Log.d(TAG, response.data.toString())
                    isLoading(false)
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, response.message!!)
                    isLoading(false)
                }
                is NetworkResult.Loading -> {
                    isLoading(true)
                }
            }
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }
    
    private fun isLoading(boolean: Boolean) {

    }
}