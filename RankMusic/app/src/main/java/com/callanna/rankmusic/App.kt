package com.callanna.rankmusic

import android.app.Application
import android.os.Handler
import com.callanna.rankmusic.dagger.compontent.ApiComponent
import com.callanna.rankmusic.dagger.compontent.DaggerApiComponent
import com.callanna.rankmusic.dagger.module.ApiModule
import com.callanna.rankmusic.dagger.module.AppModule
import javax.inject.Inject

/**
 * Created by Callanna on 2017/5/26.
 */
class App : Application() {
    init {
        instance = this
    }

    @Inject lateinit var apiComponent: ApiComponent

    val handler:Handler =  Handler()
    var currentType:String = ""
    override fun onCreate() {
        super.onCreate()
        DaggerApiComponent.builder().apiModule(ApiModule()).appModule(AppModule(this)).build().inject(this)
    }


    companion object {
        lateinit var instance: App
        var playCurrentType:String = ""
    }
}