package com.authencation.cloneriviu.support

import java.security.MessageDigest

class EncryptionPassword private constructor(){
    object Holder{ var instance = EncryptionPassword()}
    companion object{
        fun getInstance():EncryptionPassword{
            if(Holder.instance!=null) return Holder.instance
            else synchronized(this){
                Holder.instance = EncryptionPassword()
                return Holder.instance
            }
        }
    }
    fun getHash(password:String,algorithm:String):String{
        val hash = MessageDigest.getInstance(algorithm).digest(password.toByteArray())
        return hash.joinToString(""){ "%x".format(it) }
    }
}