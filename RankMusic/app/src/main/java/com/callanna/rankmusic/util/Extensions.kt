package com.callanna.rankmusic.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.callanna.rankmusic.App

/**
 * Created by Callanna on 2017/5/26.
 */
fun Context.getMainComponent() = App.instance.apiComponent

fun Context.toast(msg:String, length:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, length).show()
}

fun App.d(tag :String = "RankMusic",msg:String){
  Log.d(tag,msg)
}