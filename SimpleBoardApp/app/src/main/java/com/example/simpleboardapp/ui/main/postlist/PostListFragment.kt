package com.example.simpleboardapp.ui.main.postlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.simpleboardapp.R
import com.example.simpleboardapp.databinding.FragmentPostListBinding
import com.example.simpleboardapp.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostListFragment : BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {
    private val viewModel: PostListViewModel by viewModels()

    override fun init() {

    }
}