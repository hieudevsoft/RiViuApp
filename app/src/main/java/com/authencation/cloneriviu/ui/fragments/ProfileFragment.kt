package com.authencation.cloneriviu.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.databinding.FragmentProfileBinding
import com.authencation.cloneriviu.extensions.observeOnce
import com.authencation.cloneriviu.networks.GoogleApi
import com.authencation.cloneriviu.networks.RepositoryLogin
import com.authencation.cloneriviu.support.BottomSheetDialogLogin
import com.authencation.cloneriviu.support.DataStoreLocal
import com.authencation.cloneriviu.support.LogoutResultClient
import com.authencation.cloneriviu.support.WidgetOwner
import com.authencation.cloneriviu.ui.HomeScreen
import com.authencation.cloneriviu.viewmodels.LoginViewModel
import com.authencation.cloneriviu.viewmodels.factories.LoginViewModelsFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(),BottomSheetDialogLogin.BottomSheetListener,HomeScreen.HomeScreenListener {
    private val TAG = "ProfileFragment"
    private lateinit var googleApi: GoogleApi
    lateinit var dataStoreLocal: DataStoreLocal
    lateinit var _binding: FragmentProfileBinding
    lateinit var loginViewModel: LoginViewModel
    private val binding get() = _binding
    lateinit var loginBox: BottomSheetDialogLogin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        dataStoreLocal = DataStoreLocal(requireContext())
        loginViewModel =
            ViewModelProvider(this, LoginViewModelsFactory(RepositoryLogin.getInstance())).get(
                LoginViewModel::class.java
            )
        googleApi = GoogleApi.getInstance()
        googleApi.activity = requireActivity()
        onSubscribes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.layoutLogin.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            googleApi.checkPreConditions()
            if(GoogleApi.check)
            loginBoxCreate()
        }
        binding.btnLogout.setOnClickListener {
            dataStoreLocal.readOptionLogin.asLiveData().observeOnce(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated: Option Login: $it")
                loginViewModel.createInstance(it)
                loginViewModel.logout(requireActivity()).observe(viewLifecycleOwner, {
                    when (it) {
                        is LogoutResultClient.Success -> {
                            loginViewModel.didLoginSuccessFully.value = false
                            lifecycleScope.launch(Dispatchers.IO){
                                dataStoreLocal.saveOptionLogin(0)
                            }
                            WidgetOwner.gone(binding.btnLogout)
                            WidgetOwner.visibile(binding.layoutLogin)
                        }
                        is LogoutResultClient.Error -> {
                            loginViewModel.didLoginSuccessFully.value = true
                            WidgetOwner.gone(binding.layoutLogin)
                            WidgetOwner.visibile(binding.btnLogout)
                        }

                    }
                })
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onSubscribes() {
        loginViewModel.getLoginSuccess().observe(viewLifecycleOwner, {
            Log.d(TAG, "onSubscribes: Already login app $it")
            if (it) {
                Toast.makeText(requireContext(), "Hello : "+FirebaseAuth.getInstance().currentUser?.displayName, Toast.LENGTH_SHORT).show()
                WidgetOwner.gone(binding.layoutLogin)
                WidgetOwner.visibile(binding.btnLogout)
                try {
                    loginBox.dismiss()
                }catch (e:Exception){

                }
            } else {
                WidgetOwner.gone(binding.btnLogout)
                WidgetOwner.visibile(binding.layoutLogin)
            }
        })

    }

    override fun onDestroy() {
        _binding == null
        super.onDestroy()
    }

    private fun loginBoxCreate() {
        loginBox = BottomSheetDialogLogin.newInstance()
        loginBox.lifeCycleOwner = viewLifecycleOwner
        loginBox.show(requireActivity().supportFragmentManager, "loginbox")
        loginBox.bottomSheetListener = this

    }

    override fun onLoginFacebook(state: Boolean) {
        loginViewModel.didLoginSuccessFully.value = state
    }

    override fun onLoginGoogle(state: Boolean) {
        loginViewModel.didLoginSuccessFully.value = state
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HomeScreen) context.homeScreenListener = this
    }

}