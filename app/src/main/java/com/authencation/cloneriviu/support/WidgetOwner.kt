package com.authencation.cloneriviu.support

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.extensions.observeOnce
import com.authencation.cloneriviu.networks.RepositoryLogin
import com.authencation.cloneriviu.networks.SignInWithFaceBook
import com.authencation.cloneriviu.networks.SignInWithGoogle
import com.authencation.cloneriviu.ui.fragments.ProfileFragment
import com.authencation.cloneriviu.viewmodels.LoginViewModel
import com.authencation.cloneriviu.viewmodels.factories.LoginViewModelsFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object WidgetOwner {
    fun makeSnackbar(view: View, isConnection: Boolean) {
        val color: Int?
        val message: String
        if (isConnection) {
            message = "Internet connected"
            color = Color.WHITE
        } else {
            message = "Internet disconnected"
            color = Color.RED
        }
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val viewSnackbar = snackbar.view
        viewSnackbar.findViewById<TextView>(R.id.snackbar_text).setTextColor(color)
        snackbar.show()
    }

    fun invisible(view:View){
        view.visibility = View.INVISIBLE
    }
    fun gone(view:View){
        view.visibility = View.GONE
    }
    fun visibile(view:View){
        view.visibility = View.VISIBLE
    }
}
class BottomSheetDialogLogin : BottomSheetDialogFragment() {
    private val TAG = "BottomSheetDialogLogin"
    lateinit var loginViewModel: LoginViewModel
    lateinit var lifeCycleOwner: LifecycleOwner
    lateinit var btnFaceBook: Button
    lateinit var btnGoogle: Button
    lateinit var btnOthers: Button
    lateinit var bottomSheetListener:BottomSheetListener
    interface BottomSheetListener{
        fun onLoginFacebook(state:Boolean)
    }
    object Holder {
        val instance = BottomSheetDialogLogin()
    }

    companion object {
        fun newInstance(): BottomSheetDialogLogin {
            return Holder.instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.MyBottomSheetDialogTheme)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_login, null)
        bottomSheetDialog.setContentView(view)
        initView(view)
        loginViewModel =
            ViewModelProvider(this, LoginViewModelsFactory(RepositoryLogin.getInstance())).get(
                LoginViewModel::class.java
            )
        controlEvent(lifeCycleOwner)
        return bottomSheetDialog
    }

    private fun initView(view: View) {
        Log.d(TAG, "initView: initView")
        btnFaceBook = view.findViewById(R.id.btnLoginFaceBook)
        btnGoogle = view.findViewById(R.id.btnLoginGoogle)
        btnOthers = view.findViewById(R.id.btnLoginNormal)
    }
    private fun controlEvent(lifeCycleOwner:LifecycleOwner){
        btnFaceBook.setOnClickListener {
            loginViewModel.createInstance(1)
                loginViewModel.login(requireActivity()).observe(lifeCycleOwner, {
                    Log.d(TAG, "controlEvent: ${loginViewModel.repository.loginInstance} ${SignInWithFaceBook.getInstance()}")
                    when (it) {
                        is LoginResultClient.Success -> {
                            Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT).show()
                            GlobalScope.launch(Dispatchers.IO) {
                                val dataStoreLocal = DataStoreLocal(requireContext())
                                dataStoreLocal.saveOptionLogin(1)
                            }
                            bottomSheetListener.onLoginFacebook(true)
                        }
                        is LoginResultClient.Error -> {
                            bottomSheetListener.onLoginFacebook(false)
                            Log.d(TAG, "show: ${it.message}")
                            Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                        }
                        else -> Log.d(TAG, "show: Error stranger")
                    }
                })
        }

        btnGoogle.setOnClickListener {
            loginViewModel.createInstance(2)
            SignInWithGoogle.getInstance().createRequest(requireActivity())
            SignInWithGoogle.getInstance().signIn(requireActivity())
        }
    }
}