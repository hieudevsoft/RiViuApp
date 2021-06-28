package com.authencation.cloneriviu.adapters.Items

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.authencation.cloneriviu.databinding.ItemLayoutExploreBinding
import com.authencation.cloneriviu.databinding.ItemOptionsOneBinding
import com.authencation.cloneriviu.model.ItemOptionsOne
import com.authencation.cloneriviu.model.ItemPostExplore
import com.authencation.cloneriviu.util.OptionsOneDiffUtil
import com.authencation.cloneriviu.util.PostExploreDiffUtil


class ItemCardExploreAdapter: RecyclerView.Adapter<ItemCardExploreAdapter.MyViewHolder>() {
    private val TAG = "ItemOptionsAdapter"
    private var items = emptyList<ItemPostExplore>()
    class MyViewHolder(private val binding:ItemLayoutExploreBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ItemPostExplore){
            binding.data = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(viewGroup: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemLayoutExploreBinding.inflate(layoutInflater,viewGroup,false)
                return MyViewHolder(binding)
            }
        }
    }
    fun setData(newList:List<ItemPostExplore>){
        val diffUtilCallBack = PostExploreDiffUtil(items,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        items = newList
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}