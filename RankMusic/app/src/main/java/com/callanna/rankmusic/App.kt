package com.callanna.rankmusic

import android.os.Handler
import android.support.multidex.MultiDexApplication
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.dagger.compontent.ApiComponent
import com.callanna.rankmusic.dagger.compontent.DaggerApiComponent
import com.callanna.rankmusic.dagger.module.ApiModule
import com.callanna.rankmusic.dagger.module.AppModule
import javax.inject.Inject

/**
 * Created by Callanna on 2017/5/26.
 */
class App : MultiDexApplication()  {
    init {
        instance = this
    }

    @Inject lateinit var apiComponent: ApiComponent

    val handler:Handler =  Handler()
    var currentType:String = ""
    var currentSong: Music ?= null
    var currentPosition:Int = 0

    override fun onCreate() {
        super.onCreate()
        DaggerApiComponent.builder().apiModule(ApiModule()).appModule(AppModule(this)).build().inject(this)
    }

    companion object {
        lateinit var instance: App
        var playCurrentType:String = ""
    }

}