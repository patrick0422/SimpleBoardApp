package com.example.simpleboardapp.ui.main.upload

import androidx.fragment.app.viewModels
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.post.PostRequest
import com.example.simpleboardapp.databinding.FragmentUploadBinding
import com.example.simpleboardapp.ui.main.MainViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.NetworkResult

class UploadFragment : BaseFragment<FragmentUploadBinding>(R.layout.fragment_upload) {
    private val mainViewModel: MainViewModel by viewModels()

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
        } else if (content.isBlank()) {
            showToast("내용을 입력해주세요.")
        }

        mainViewModel.addPost(PostRequest(title, content, tags))

        mainViewModel.addPostResponse.observe(this, { response ->
            when(response) {
                is NetworkResult.Success -> {
                    showToast("성공!")
                }
                is NetworkResult.Error -> {
                    showToast(response.message!!)
                }
                is NetworkResult.Loading -> {

                }
            }

        })
    }

    private fun processTags(tags: String): String {
        return tags.replace(" ", "#")
    }
}