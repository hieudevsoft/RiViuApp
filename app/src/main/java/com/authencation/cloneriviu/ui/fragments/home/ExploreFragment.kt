package com.authencation.cloneriviu.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.databinding.FragmentExploreBinding
import com.facebook.all.All
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems


class ExploreFragment : Fragment() {
    private val TAG = "ExploreFragment" 
    private var _binding : FragmentExploreBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: onCreateView")
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager, FragmentPagerItems.with(requireContext())
                .add("Tất cả", AllFragment::class.java) // fragment class of tab 1
                .add("Gần tôi", AllFragment::class.java) // fragment class of tab 2
                .add("Đồ ăn", AllFragment::class.java) // fragment class of tab 2
                .add("Du lịch", AllFragment::class.java) // fragment class of tab 2
                .create()
        )

        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        val viewPagerTab = binding.viewPagerTab
        viewPagerTab.setViewPager(viewPager)
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }
}