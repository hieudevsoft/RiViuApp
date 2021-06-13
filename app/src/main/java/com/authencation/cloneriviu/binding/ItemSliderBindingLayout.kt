package com.authencation.cloneriviu.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ItemSliderBindingLayout {
    @BindingAdapter("loadImageFromLocal")
    @JvmStatic
    fun loadImageFromLocal(imageView: ImageView,url:Int){
        imageView.setImageResource(url)
    }
}