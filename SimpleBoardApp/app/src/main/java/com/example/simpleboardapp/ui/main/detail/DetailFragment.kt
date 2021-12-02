package com.example.simpleboardapp.ui.main.detail

import android.util.Log
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

    override fun init() {
        mainViewModel.getPost(args.postId)

        mainViewModel.getPostResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val post = response.data!!
                    Log.d(TAG, "포스트 불러오기 성공: $post")

                    binding.textTitle.text = post.title
                    binding.textContent.text = post.content
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "포스트 불러오기 실패: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    // TODO : 실행 돌려서 로그값 보기
                }
            }
        })
    }
}