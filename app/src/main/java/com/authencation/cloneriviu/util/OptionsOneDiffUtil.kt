package com.authencation.cloneriviu.util

import androidx.recyclerview.widget.DiffUtil
import com.authencation.cloneriviu.model.ItemOptionsOne

class OptionsOneDiffUtil(private val oldList:List<ItemOptionsOne>,private val newList:List<ItemOptionsOne>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == newList[newItemPosition]
    }
}