package com.authencation.cloneriviu.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.ItemOptionsAdapter
import com.authencation.cloneriviu.adapters.ItemOptionsTwoAdapter
import com.authencation.cloneriviu.databinding.FragmentHomeBinding
import com.authencation.cloneriviu.receiver.NetworkReciever
import com.authencation.cloneriviu.support.DataStoreLocal
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.support.WidgetOwner


class HomeFragment : Fragment() {
    private val TAG = "HomeFragment" 
    private var _binding: FragmentHomeBinding?=null
    lateinit var itemOptionsAdapter: ItemOptionsAdapter
    lateinit var itemOptionsTwoAdapter: ItemOptionsTwoAdapter
    lateinit var dataStoreLocal: DataStoreLocal
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        dataStoreLocal = DataStoreLocal(requireContext().applicationContext)
        dataStoreLocal.readLocationData.asLiveData().observe(viewLifecycleOwner, {
            binding?.location = it
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.containerSearch?.setOnClickListener {
            Toast.makeText(context, "Click search", Toast.LENGTH_SHORT).show()
        }
        initOptionsOne()
        initOptionsTwo()
        controlRefreshLayout()
        openSearchLocations()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        Log.d(TAG, "onResume: onResume")
        if(NetworkReciever.hasInternetConnection(context)) {
            binding?.shimmerHeader?.stopShimmer()
            binding?.shimmerHeader?.hideShimmer()
            visibleItem()
            binding?.shimmerOptionsOne?.hideShimmer()
            binding?.shimmerOptionsTwo?.hideShimmer()
            itemOptionsAdapter.setData(Support.createListOptionsOne())
            itemOptionsTwoAdapter.setData(Support.createListOptionsTwo())
        }else{
            binding?.shimmerHeader?.startShimmer()
            binding?.shimmerHeader?.showShimmer(true)
        }

        super.onResume()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: onDestroy")
        _binding = null
        super.onDestroy()
    }
    fun controlRefreshLayout(){
        binding?.refreshLayout?.setColorSchemeColors(context?.getColor(R.color.orange)!!)
        binding?.refreshLayout?.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding!!.refreshLayout.isRefreshing =false
                itemOptionsAdapter.setData(Support.createListOptionsOne())
                itemOptionsTwoAdapter.setData(Support.createListOptionsTwo())
                visibleItem()
            },2000L)
        }
    }

    fun visibleItem(){
        binding?.shimmerHeader?.visibility = View.GONE
        binding?.containerMember?.let { WidgetOwner.visibile(it) }
        binding?.containerChooseAddress?.let { WidgetOwner.visibile(it) }
        binding?.containerSearch?.let { WidgetOwner.visibile(it) }
        binding?.imgMemberShip?.let { WidgetOwner.visibile(it) }
    }

    fun initOptionsOne(){
        itemOptionsAdapter = ItemOptionsAdapter()
        binding?.shimmerOptionsOne?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding?.shimmerOptionsOne?.adapter = itemOptionsAdapter
        binding?.shimmerOptionsOne?.showShimmer()
    }

    fun initOptionsTwo(){
        itemOptionsTwoAdapter = ItemOptionsTwoAdapter()
        binding?.shimmerOptionsTwo?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding?.shimmerOptionsTwo?.adapter = itemOptionsTwoAdapter
        binding?.shimmerOptionsTwo?.showShimmer()
    }

    fun openSearchLocations(){
        binding?.containerChooseAddress?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchLocationFragment)
        }
    }
}