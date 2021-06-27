package com.authencation.cloneriviu.adapters.Items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.support.DataStoreLocal
import com.authencation.cloneriviu.util.OldLocationDiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemOldLocationAdapter(private val context: Context):
    RecyclerView.Adapter<ItemOldLocationAdapter.MyViewHolder>() {
    var addresses = ArrayList<String>()
    private val dataStore = DataStoreLocal(context)
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(item:String){
            itemView.findViewById<TextView>(R.id.tvOldLocation).text = item
        }
        companion object{
            fun from(viewGroup: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val view = layoutInflater.inflate(R.layout.item_old_address,null)
                return MyViewHolder(view)
            }
        }
    }
    fun setData(newList:ArrayList<String>){
        val diffUtilCallBack = OldLocationDiffUtil(addresses,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        addresses.clear()
        addresses.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(addresses[position])
        holder.itemView.rootView.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                dataStore.saveLocationData(addresses[position])
            }
            Navigation.findNavController(holder.itemView).navigate(R.id.action_searchLocationFragment_to_homeFragment)
        }
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

}