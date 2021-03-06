package com.example.simpleboardapp.ui.main.postlist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
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
    private val mainViewModel: MainViewModel by activityViewModels()
    private val mAdapter: PostListAdapter by lazy { PostListAdapter() }

    override fun init() {
        binding.postView.adapter = mAdapter

        binding.fabAddPost.setOnClickListener {
            it.findNavController().navigate(R.id.action_postListFragment_to_uploadFragment)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            getPosts()
        }

        binding.buttonProfile.setOnClickListener {
            with(mainViewModel.user.value!!) {
                Log.d(TAG, "init: $this")
                AlertDialog.Builder(context)
                    .setTitle(nickname)
                    .setMessage(createdAt)
                    .setPositiveButton("로그아웃") { _, _ ->
                        mainViewModel.deleteUser()
                    }
                    .setNeutralButton("닫기") { _, _ ->

                    }
                    .show()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getPosts(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isBlank())
                    getPosts()
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getPosts()
    }

    private fun getPosts() {
        getPosts("")
    }

    private fun getPosts(keyword: String) {
        isLoading(true)
        mainViewModel.getPosts(SearchRequest(1, keyword))

        mainViewModel.getPostsResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val list = response.data!!.asReversed() // 최신 글 순으로
                    if (mAdapter.postList != list)
                        mAdapter.setData(list)
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

    private fun isLoading(loading: Boolean) {
        if (loading)
            binding.shimmerView.showShimmer()
        else
            binding.shimmerView.hideShimmer()
    }
}