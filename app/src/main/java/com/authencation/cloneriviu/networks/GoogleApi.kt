package com.authencation.cloneriviu.networks

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.widget.Toast
import com.authencation.cloneriviu.support.Constants
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class GoogleApi private constructor(){
    lateinit var activity:Activity
    object Holder{
        val instance = GoogleApi()
    }
    companion object{
        var check = true
        fun getInstance() = Holder.instance
    }
    private val googleApiAvailability by lazy { GoogleApiAvailability.getInstance() }
    private val googleConnectionStatus by lazy { googleApiAvailability.isGooglePlayServicesAvailable(activity) }
    private fun isGooglePlayServicesAvailable() = googleConnectionStatus==ConnectionResult.SUCCESS
    private fun showGPSErrorDialog(){
        val dialog = googleApiAvailability.getErrorDialog(
            activity,googleConnectionStatus, Constants.GOOGLE_REQUEST_REQUEST
        )
        dialog.setTitle(Html.fromHtml("<font color='#ffab00'>Error!</font>"))
        dialog.window?.setBackgroundDrawable(ColorDrawable(activity.getColor(android.R.color.holo_red_light)))
        dialog.show()
    }
    private fun acquireGooglePlayServices(){
        if(googleApiAvailability.isUserResolvableError(googleConnectionStatus)) {
            showGPSErrorDialog()
            check = false
        }
        else Toast.makeText(activity, "Cannot find or install Google Play Services", Toast.LENGTH_SHORT).show()
    }
    fun checkPreConditions(){
        if(!isGooglePlayServicesAvailable()) acquireGooglePlayServices()
        else {
            check = true
            Toast.makeText(activity, "Google Play Services available", Toast.LENGTH_SHORT).show()
        }
    }
}