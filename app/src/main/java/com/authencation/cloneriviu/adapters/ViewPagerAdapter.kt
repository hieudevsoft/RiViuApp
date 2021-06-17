package com.authencation.cloneriviu.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.ui.fragments.home.ExploreFragment
import com.authencation.cloneriviu.ui.fragments.home.FollowFragment

class ViewPagerAdapter(private val mContext: Context): PagerAdapter() {
    override fun getCount(): Int {
        return 2
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layout:View?=null
        when(position){
            0-> layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_explore,container,false)
            1-> layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_follow,container,false)
        }
        container.addView(layout,position)
        return layout!!
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }
}