package com.authencation.cloneriviu.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.load
import coil.size.Scale
import com.authencation.cloneriviu.R

object ItemOptionsBoundBindingLayout {
    @BindingAdapter("loadImageFromInternet")
    @JvmStatic
    fun loadImageFromInternet(imageView: ImageView,url:String?){
        imageView.load(url){
            crossfade(true)
            crossfade(1000)
            scale(Scale.FIT)
            placeholder(R.drawable.no_image)
            size(width = 300,height = 140)
        }
    }
}