package com.authencation.cloneriviu.ui.fragments.home

import android.annotation.SuppressLint
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
import androidx.viewpager.widget.ViewPager
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.Items.ItemOptionsAdapter
import com.authencation.cloneriviu.adapters.Items.ItemOptionsTwoAdapter
import com.authencation.cloneriviu.databinding.FragmentHomeBinding
import com.authencation.cloneriviu.receiver.NetworkReciever
import com.authencation.cloneriviu.support.DataStoreLocal
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.support.WidgetOwner
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems


class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    lateinit var itemOptionsAdapter: ItemOptionsAdapter
    lateinit var itemOptionsTwoAdapter: ItemOptionsTwoAdapter
    lateinit var dataStoreLocal: DataStoreLocal
    lateinit var viewPagerAdapter: FragmentPagerItemAdapter
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

        viewPagerAdapter = FragmentPagerItemAdapter(childFragmentManager, FragmentPagerItems.with(requireContext())
            .add("Explore",ExploreFragment::class.java)
            .add("Follow",FollowFragment::class.java)
            .create()
        )
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
        initViewPager()
        initTextView()
        controlTextView(0)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initTextView() {
        binding?.tvExplore?.setOnClickListener {
            controlTextView(0)
            binding?.viewPager?.currentItem = 0
        }
        binding?.tvFollow?.setOnClickListener {
            controlTextView(1)
            binding?.viewPager?.currentItem = 1
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume: onResume")
        if (NetworkReciever.hasInternetConnection(context)) {
            binding?.shimmerHeader?.stopShimmer()
            binding?.shimmerHeader?.hideShimmer()
            visibleItem()
            binding?.shimmerOptionsOne?.hideShimmer()
            binding?.shimmerOptionsTwo?.hideShimmer()
            itemOptionsAdapter.setData(Support.createListOptionsOne())
            itemOptionsTwoAdapter.setData(Support.createListOptionsTwo())
        } else {
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

    private fun controlRefreshLayout() {
        binding?.refreshLayout?.setColorSchemeColors(context?.getColor(R.color.orange)!!)
        binding?.refreshLayout?.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding!!.refreshLayout.isRefreshing = false
                itemOptionsAdapter.setData(Support.createListOptionsOne())
                itemOptionsTwoAdapter.setData(Support.createListOptionsTwo())
                visibleItem()
            }, 2000L)
        }
    }

    private fun visibleItem() {
        binding?.shimmerHeader?.visibility = View.GONE
        binding?.containerMember?.let { WidgetOwner.visibile(it) }
        binding?.containerChooseAddress?.let { WidgetOwner.visibile(it) }
        binding?.containerSearch?.let { WidgetOwner.visibile(it) }
        binding?.imgMemberShip?.let { WidgetOwner.visibile(it) }
    }

    private fun initOptionsOne() {
        itemOptionsAdapter = ItemOptionsAdapter()
        binding?.shimmerOptionsOne?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding?.shimmerOptionsOne?.adapter = itemOptionsAdapter
        binding?.shimmerOptionsOne?.showShimmer()
    }

    private fun initOptionsTwo() {
        itemOptionsTwoAdapter = ItemOptionsTwoAdapter()
        binding?.shimmerOptionsTwo?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding?.shimmerOptionsTwo?.adapter = itemOptionsTwoAdapter
        binding?.shimmerOptionsTwo?.showShimmer()
    }

    private fun openSearchLocations() {
        binding?.containerChooseAddress?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchLocationFragment)
        }
    }
    private fun controlTextView(position:Int){
        when (position) {
            0 -> {
                binding?.isCheckedExplore = true
                binding?.isCheckedFollow = false
                Support.setPresentTextView(
                    binding!!.tvExplore,
                    1.2f,
                    resources.getColor(android.R.color.black, null)
                )
                Support.setPresentTextView(
                    binding!!.tvFollow,
                    1f,
                    resources.getColor(R.color.gray_black, null)
                )
            }
            1 -> {
                binding?.isCheckedFollow = true
                binding?.isCheckedExplore = false
                Support.setPresentTextView(
                    binding!!.tvExplore,
                    1f,
                    resources.getColor(R.color.gray_black, null)
                )
                Support.setPresentTextView(
                    binding!!.tvFollow,
                    1.2f,
                    resources.getColor(android.R.color.black, null)
                )

            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initViewPager() {
        binding?.viewPager?.adapter = viewPagerAdapter
        binding?.viewPager?.setPageTransformer(true, CubeOutTransformer(), 0)
        binding?.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d(
                    TAG,
                    "onPageScrolled: Position: $position, PositionOffset: $positionOffset, PositionOffsetPixels: $positionOffsetPixels"
                )
            }

            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected: $position")
                controlTextView(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d(TAG, "onPageScrollStateChanged: $state ${ViewPager.SCROLL_STATE_IDLE}")
                enableDisableSwipeRefresh( state == ViewPager.SCROLL_STATE_IDLE )
            }
        })

    }
    private fun enableDisableSwipeRefresh(enable: Boolean) {
        if(binding?.refreshLayout!=null){
            binding?.refreshLayout?.isEnabled = enable
        }
    }
}