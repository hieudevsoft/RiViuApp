package com.authencation.cloneriviu.support

sealed class LoginResultClient<T>( val state:T?=null,
                              val message:String?=null)

{
    class Success<T>(state: T?): LoginResultClient<T>(state)
    class Error<T>(message: String?, state: T? = null): LoginResultClient<T>(state, message)
}
