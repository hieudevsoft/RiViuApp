package com.authencation.cloneriviu.binding

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.authencation.cloneriviu.R

object LoaginScreenBindingLayout {
    @BindingAdapter("loadBackgroundButton")
    @JvmStatic
    fun loadBackgroundButton(button: Button, isEnable:Boolean){
        if(isEnable)
        button.setBackgroundResource(R.drawable.custom_login_button_enabled)
        else button.setBackgroundResource(R.drawable.custom_login_button_notenabled)
    }
}