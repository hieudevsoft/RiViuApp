package com.authencation.cloneriviu.bussiness_logics

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.ScrollView
import com.authencation.cloneriviu.adapters.Items.ItemPopularAddressAdapter
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.support.WidgetOwner

class SearchLocation(
    private val window: Window,
    private val adapter: ItemPopularAddressAdapter,
    private val image: ImageView,
    private val lyAddress: ScrollView
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Support.setFlagFullScreen(window)
        val name = s.toString()
        var listAll: ArrayList<String>
        if (name.isBlank() || name.isEmpty()) {
            WidgetOwner.gone(image)
            WidgetOwner.visibile(lyAddress)
            listAll = Support.createListConvinceVietNam()
        }
        else {
            listAll = Support.createListConvinceVietNam()
            listAll = listAll.filter { item->item.contains(name)
            } as ArrayList<String>
            Log.d("TAG", "onTextChanged: ${listAll.size}")
            if (listAll.isEmpty()) {
                WidgetOwner.visibile(image)
                WidgetOwner.gone(lyAddress)
            } else {
                WidgetOwner.gone(image)
                WidgetOwner.visibile(lyAddress)
            }
        }
        adapter.setData(listAll)
    }

    override fun afterTextChanged(s: Editable?) {

    }
}