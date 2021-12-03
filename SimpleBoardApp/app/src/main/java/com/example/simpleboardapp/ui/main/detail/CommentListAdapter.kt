package com.example.simpleboardapp.ui.main.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleboardapp.data.comment.Comment
import com.example.simpleboardapp.data.post.Post
import com.example.simpleboardapp.databinding.CommentListItemBinding
import com.example.simpleboardapp.util.BaseDiffUtil

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {
    var commentList: List<Comment> = emptyList()

    class CommentViewHolder(private val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, comment: Comment) = binding.apply {
            binding.textId.text = "#${position + 1}번째 댓글"
            binding.textContent.text = comment.content
        }

        companion object {
            fun from(parent: ViewGroup): CommentViewHolder =
                CommentViewHolder(CommentListItemBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder =
        CommentViewHolder.from(parent)

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(position, commentList[position])
    }

    override fun getItemCount(): Int = commentList.size

    fun setData(newData: List<Comment>) {
        val commentListDiffUtil = BaseDiffUtil(commentList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(commentListDiffUtil)

        commentList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}