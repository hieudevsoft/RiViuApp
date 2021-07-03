package com.authencation.cloneriviu.ui.fragments.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.Items.ItemCardExploreAdapter
import com.authencation.cloneriviu.model.ItemPostExplore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class AllFragment : Fragment() {
    private val TAG = "AllFragment"
    lateinit var recycleView:RecyclerView
    lateinit var itemCardExploreAdapter: ItemCardExploreAdapter
    lateinit var tempData:MutableList<ItemPostExplore>
    private var isloading = false;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        Log.d(TAG, "onCreateView: OnCreateView")
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: OnCreate")
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycleView = view.findViewById(R.id.recycleView)
        initRecycleView()
        initScrollListener()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initRecycleView(){
        tempData = mutableListOf(
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
        )
        itemCardExploreAdapter = ItemCardExploreAdapter()
        itemCardExploreAdapter.setData(tempData)
        recycleView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recycleView.adapter = itemCardExploreAdapter
    }
    private fun initScrollListener(){
        recycleView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.d(TAG, "onScrollStateChanged: State: $newState")
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d(TAG, "onScrolled: Position: $dx, $dy")
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                if(!isloading){
                        val lastVisibleItemPositions = layoutManager.findLastCompletelyVisibleItemPositions(null)
                        val lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
                        if(lastVisibleItemPosition!=RecyclerView.NO_POSITION && lastVisibleItemPosition==recyclerView.adapter?.itemCount!!-1){
                            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                            loadMore()
                            isloading = true
                        }
                }
            }
        })
    }

    private fun loadMore() {
        Handler(Looper.getMainLooper()).postDelayed({
            loadData()
            isloading = false
            Log.d(TAG, "loadMore: ${itemCardExploreAdapter.items.size}")
        },
        2000)
        }
    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
    fun loadData(){
        tempData.add(
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100)
        )
        tempData.add(
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100)
        )
        itemCardExploreAdapter.setData(tempData)
    }
}