package com.authencation.cloneriviu.binding

import android.view.View
import androidx.databinding.BindingAdapter

object FragmentHomeLayoutBinding {
    @BindingAdapter("visibilityDot")
    @JvmStatic
    fun visibilityDot(view: View, isChecked:Boolean){
        when(isChecked){
            true-> view.visibility = View.GONE
            false->view.visibility = View.VISIBLE
            else ->view.visibility = View.VISIBLE
        }
    }
}