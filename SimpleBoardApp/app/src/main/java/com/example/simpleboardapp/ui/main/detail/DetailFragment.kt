package com.example.simpleboardapp.ui.main.detail

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.simpleboardapp.R
import com.example.simpleboardapp.databinding.FragmentDetailBinding
import com.example.simpleboardapp.ui.main.MainViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.Constants.Companion.TAG
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args by navArgs<DetailFragmentArgs>()
    private val commentAdapter by lazy { CommentListAdapter() }

    override fun init() {
        binding.commentView.adapter = commentAdapter

        binding.buttonAdd.setOnClickListener {
            val comment = binding.editComment.text.toString()
            if (comment.isBlank()) {
                showToast("댓글을 입력해주세요.")
                return@setOnClickListener
            }

            isLoading(true)
            mainViewModel.addComment(args.postId, comment)
            mainViewModel.addCommentResponse.observe(this, { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        binding.editComment.text.clear()
                        mainViewModel.getPost(args.postId)
                        showToast("댓글이 등록되었습니다.")
                        isLoading(false)
                    }
                    is NetworkResult.Error -> {
                        showToast("댓글 등록에 실패했습니다.")
                        isLoading(false)
                    }
                    is NetworkResult.Loading -> isLoading(true)
                }
            })
        }

        mainViewModel.getPost(args.postId)

        mainViewModel.getPostResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val post = response.data!!
                    Log.d(TAG, "포스트 불러오기 성공: $post")

                    binding.textTitle.text = post.title
                    binding.textContent.text = post.content
                    binding.textTags.text = post.tags
                    commentAdapter.setData(post.comments)

                    isLoading(false)
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "포스트 불러오기 실패: ${response.message}")
                    isLoading(false)
                }
                is NetworkResult.Loading -> {
                    isLoading(true)
                }
            }
        })
    }

    private fun isLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.buttonAdd.isClickable = !loading
    }
}