package com.authencation.cloneriviu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.authencation.cloneriviu.databinding.ItemSliderBinding
import com.authencation.cloneriviu.model.ItemSlider
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderOnboardingAdapter: SliderViewAdapter<SliderOnboardingAdapter.MyViewHolder>() {
    private var itemsSlider = emptyList<ItemSlider>()
    class MyViewHolder(private val binding:ItemSliderBinding):SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(item:ItemSlider){
            binding.itemSlider = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(viewGroup: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemSliderBinding.inflate(layoutInflater,viewGroup,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun getCount(): Int {
       return itemsSlider.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): MyViewHolder {
        return parent?.let { MyViewHolder.from(it) }!!
    }

    override fun onBindViewHolder(
        viewHolder: MyViewHolder?,
        position: Int
    ) {
        viewHolder?.bind(itemsSlider[position])
    }
    fun setData(newList:List<ItemSlider>){
        itemsSlider = newList
        notifyDataSetChanged()
    }
}