package com.authencation.cloneriviu.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.Items.ItemCardExploreAdapter
import com.authencation.cloneriviu.model.ItemPostExplore


class AllFragment : Fragment() {
    lateinit var recycleView:RecyclerView
    lateinit var itemCardExploreAdapter: ItemCardExploreAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycleView = view.findViewById(R.id.recycleView)
        initRecycleView()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initRecycleView(){
        val tempData = listOf(
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
            ItemPostExplore("https://images.pexels.com/photos/704569/pexels-photo-704569.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260","Một chút Review về Bin béo","https://images.pexels.com/photos/4513204/pexels-photo-4513204.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260","Lê Tuán Hiệp",100),
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
}