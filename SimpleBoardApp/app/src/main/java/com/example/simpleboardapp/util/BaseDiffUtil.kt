package com.example.simpleboardapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.simpleboardapp.data.post.Post

class BaseDiffUtil(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}