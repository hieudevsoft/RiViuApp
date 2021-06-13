package com.authencation.cloneriviu.support

sealed class LogoutResultClient<T>(val state:T?=null,
                                   val message:String?=null)

{
    class Success<T>(state: T?): LogoutResultClient<T>(state)
    class Error<T>(message: String?, state: T? = null): LogoutResultClient<T>(state, message)
}
