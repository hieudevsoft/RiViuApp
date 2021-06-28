package com.authencation.cloneriviu.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.load
import coil.size.Scale
import com.authencation.cloneriviu.R

object ItemExploreBindingLayout {
    @BindingAdapter("loadImageExploreFromInternet")
    @JvmStatic
    fun loadImageExploreFromInternet(imageView: ImageView,url:String?){
        imageView.load(url){
            crossfade(true)
            crossfade(1000)
            scale(Scale.FIT)
            placeholder(R.drawable.no_image)
            size(width = 280,height = 360)
        }
    }
    @BindingAdapter("loadAvatarFromInternet")
    @JvmStatic
    fun loadAvatarFromInternet(imageView: ImageView,url:String?){
        imageView.load(url){
            crossfade(true)
            crossfade(1000)
            scale(Scale.FIT)
            placeholder(R.drawable.no_image)
            size(width = 36,height = 36)
        }
    }
}