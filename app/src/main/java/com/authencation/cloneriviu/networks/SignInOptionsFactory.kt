package com.authencation.cloneriviu.networks

import com.authencation.cloneriviu.model.BaseLogin


class SignInOptionsFactory {
    companion object {
        @JvmStatic
        lateinit var loginInstance: BaseLogin
        fun createInstance(option: Int) {
            when (option) {
                1 -> loginInstance = SignInWithFaceBook.getInstance()
                2 -> loginInstance = SignInWithGoogle.getInstance()
            }
        }
    }

}