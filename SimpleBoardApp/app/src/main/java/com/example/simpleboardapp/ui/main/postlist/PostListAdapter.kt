package com.example.simpleboardapp.ui.main.postlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.post.Post
import com.example.simpleboardapp.databinding.PostListItemBinding
import com.example.simpleboardapp.ui.main.detail.DetailFragmentArgs
import com.example.simpleboardapp.util.BaseDiffUtil

class PostListAdapter() : RecyclerView.Adapter<PostListAdapter.PostListViewHolder>() {
    var postList: List<Post> = emptyList()

    class PostListViewHolder(private val binding: PostListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = binding.apply {
            textId.text = post.id.toString()
            textTitle.text = post.title
            textTags.text = post.tags

            cardView.setOnClickListener {
                it.findNavController().navigate(PostListFragmentDirections.actionPostListFragmentToDetailFragment(post.id))
            }
        }

        companion object {
            fun from(parent: ViewGroup): PostListViewHolder = PostListViewHolder(
                PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder = PostListViewHolder.from(parent)

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

    fun setData(newData: List<Post>) {
        val postListDiffUtil = BaseDiffUtil(postList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(postListDiffUtil)

        postList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}