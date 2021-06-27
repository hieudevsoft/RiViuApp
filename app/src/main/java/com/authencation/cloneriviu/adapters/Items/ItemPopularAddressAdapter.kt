package com.authencation.cloneriviu.adapters.Items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import com.authencation.cloneriviu.extensions.observeOnce
import com.authencation.cloneriviu.support.DataStoreLocal
import com.authencation.cloneriviu.util.PopularAddressDiffUtil
import com.authencation.cloneriviu.viewmodels.CurrentLocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ItemPopularAddressAdapter(
    context: Context,
    private val lifeCycle:LifecycleOwner,
    private val currentLocationViewModel: CurrentLocationViewModel,
  ):
    RecyclerView.Adapter<ItemPopularAddressAdapter.MyViewHolder>() {
    var addresses = ArrayList<String>()
    private val dataStore = DataStoreLocal(context)
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(item:String){
            itemView.findViewById<TextView>(R.id.tvAddress).text = item
        }
        companion object{
            fun from(viewGroup: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val view = layoutInflater.inflate(R.layout.item_popular_address,null)
                return MyViewHolder(view)
            }
        }
    }
    fun setData(newList:ArrayList<String>){
        val diffUtilCallBack = PopularAddressDiffUtil(addresses,newList)
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
            currentLocationViewModel.deleteContentSameTableOldLocation(addresses[position])
            currentLocationViewModel.getCount.observeOnce(lifeCycle, {
                if (it > 4) {
                    currentLocationViewModel.deleteRowFirst()
                }
                currentLocationViewModel.insertLocation(
                    CurrentAddressEntity(
                        null,
                        addresses[position]
                    )
                )
                GlobalScope.launch (Dispatchers.IO){
                    dataStore.saveLocationData(addresses[position])
                }
                Navigation.findNavController(holder.itemView)
                    .navigate(R.id.action_searchLocationFragment_to_homeFragment)
            })

        }
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

}