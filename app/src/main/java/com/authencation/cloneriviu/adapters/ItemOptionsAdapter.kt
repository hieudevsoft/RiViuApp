package com.authencation.cloneriviu.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.authencation.cloneriviu.databinding.ItemOptionsOneBinding
import com.authencation.cloneriviu.model.ItemOptionsOne
import com.authencation.cloneriviu.util.OptionsOneDiffUtil


class ItemOptionsAdapter: RecyclerView.Adapter<ItemOptionsAdapter.MyViewHolder>() {
    private val TAG = "ItemOptionsAdapter"
    private var itemOptionsOne = emptyList<ItemOptionsOne>()
    class MyViewHolder(private val binding:ItemOptionsOneBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ItemOptionsOne){
            binding.data = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(viewGroup: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemOptionsOneBinding.inflate(layoutInflater,viewGroup,false)
                return MyViewHolder(binding)
            }
        }
    }
    fun setData(newList:List<ItemOptionsOne>){
        val diffUtilCallBack = OptionsOneDiffUtil(itemOptionsOne,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        itemOptionsOne = newList
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: bindView ")
        holder.bind(itemOptionsOne[position])
    }

    override fun getItemCount(): Int {
        return itemOptionsOne.size
    }

}