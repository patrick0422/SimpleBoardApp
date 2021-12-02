package com.example.simpleboardapp.ui.main.upload

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.post.PostRequest
import com.example.simpleboardapp.databinding.FragmentUploadBinding
import com.example.simpleboardapp.ui.main.MainViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.Constants.Companion.TAG
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadFragment : BaseFragment<FragmentUploadBinding>(R.layout.fragment_upload) {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init() {
        binding.buttonUpload.setOnClickListener {
            uploadPost()
        }
    }

    private fun uploadPost() {
        val title = binding.editTitle.text.toString()
        val content = binding.editContent.text.toString()
        val tags = processTags(binding.editTags.text.toString())

        if (title.isBlank()) {
            showToast("제목을 입력해주세요.")
            return
        } else if (content.isBlank()) {
            showToast("내용을 입력해주세요.")
            return
        }

        isLoading(true)
        mainViewModel.addPost(PostRequest(title, content, tags))
        mainViewModel.addPostResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is NetworkResult.Success -> {
                    showToast("성공!")
                    isLoading(false)
                    activity!!.supportFragmentManager.popBackStack()
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "uploadPost: ${response.message!!}")
                    showToast("글 작성에 실패했습니다.")
                    isLoading(false)
                }
                is NetworkResult.Loading -> {
                    isLoading(true)
                }
            }

        })
    }

    private fun isLoading(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE
        binding.buttonUpload.isClickable = !b
    }

    private fun processTags(tags: String): String {
        return tags.replace(" ", "#")
    }
}