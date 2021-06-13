package com.authencation.cloneriviu.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.authencation.cloneriviu.databinding.ItemOptionsOneBinding
import com.authencation.cloneriviu.databinding.ItemOptionsTwoBinding
import com.authencation.cloneriviu.model.ItemOptionsOne
import com.authencation.cloneriviu.model.ItemOptionsTwo
import com.authencation.cloneriviu.util.OptionsOneDiffUtil
import com.authencation.cloneriviu.util.OptionsTwoDiffUtil


class ItemOptionsTwoAdapter: RecyclerView.Adapter<ItemOptionsTwoAdapter.MyViewHolder>() {
    private val TAG = "ItemOptionsAdapter"
    private var itemOptionsTwo = emptyList<ItemOptionsTwo>()
    class MyViewHolder(private val binding:ItemOptionsTwoBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ItemOptionsTwo){
            binding.data = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(viewGroup: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemOptionsTwoBinding.inflate(layoutInflater,viewGroup,false)
                return MyViewHolder(binding)
            }
        }
    }
    fun setData(newList:List<ItemOptionsTwo>){
        val diffUtilCallBack = OptionsTwoDiffUtil(itemOptionsTwo,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        itemOptionsTwo = newList
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
        holder.bind(itemOptionsTwo[position])
    }

    override fun getItemCount(): Int {
        return itemOptionsTwo.size
    }

}