package com.authencation.cloneriviu.model

import android.app.Activity
import android.view.View
import com.authencation.cloneriviu.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetFactory(name:String) {
    var nameDialog = name
    var dialog: BottomSheetDialog? = null
    var view: View? = null
    fun createBottomSheetDialog(activity: Activity, layout: Int): BottomSheetDialog? {
        when (nameDialog) {
            "login" -> {
                view = activity.layoutInflater.inflate(layout, null)
                dialog = BottomSheetDialog(activity, R.style.SheetDialog)
                if(view!=null){
                    dialog?.setContentView(view!!)
                }
                return dialog
            }
            else-> return null
        }
    }

}