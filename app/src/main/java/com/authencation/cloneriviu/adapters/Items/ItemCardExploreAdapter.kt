package com.authencation.cloneriviu.adapters.Items

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.authencation.cloneriviu.databinding.ItemLayoutExplore2Binding
import com.authencation.cloneriviu.databinding.ItemLayoutExploreBinding
import com.authencation.cloneriviu.databinding.ItemOptionsOneBinding
import com.authencation.cloneriviu.model.ItemOptionsOne
import com.authencation.cloneriviu.model.ItemPostExplore
import com.authencation.cloneriviu.util.OptionsOneDiffUtil
import com.authencation.cloneriviu.util.PostExploreDiffUtil
import kotlin.random.Random


class ItemCardExploreAdapter: RecyclerView.Adapter<ItemCardExploreAdapter.MyViewHolder>() {
    private val TAG = "ItemOptionsAdapter"
    private var items = emptyList<ItemPostExplore>()
    class MyViewHolder(private val binding:ViewDataBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ItemPostExplore){
            if(binding is ItemLayoutExploreBinding)
            binding.data = item
            else if(binding is ItemLayoutExplore2Binding)
            binding.data = item
            binding.executePendingBindings()
        }
        object Item{
            val TYPE_1=0
            val TYPE_2=1
        }
        companion object{

            fun from(viewGroup: ViewGroup,viewType: Int): MyViewHolder {
                lateinit var binding: ViewDataBinding
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                when(viewType){
                    Item.TYPE_1 ->  binding = ItemLayoutExploreBinding.inflate(layoutInflater,viewGroup,false)
                    Item.TYPE_2 ->  binding = ItemLayoutExplore2Binding.inflate(layoutInflater,viewGroup,false)
                    else->binding = ItemLayoutExploreBinding.inflate(layoutInflater,viewGroup,false)
                }
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
        return MyViewHolder.from(parent,viewType)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: bindView ")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        when(position){
            0->return(MyViewHolder.Item.TYPE_1)
            1->return(MyViewHolder.Item.TYPE_2)
            else->return(MyViewHolder.Item.TYPE_1)
        }
    }
}