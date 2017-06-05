package com.callanna.rankmusic.dagger.compontent

import com.callanna.rankmusic.App
import com.callanna.rankmusic.dagger.module.ApiModule
import dagger.Component

/**
 * Created by Callanna on 2017/5/26.
 */
@Component(modules = arrayOf(ApiModule::class))
interface ApiComponent{

    fun inject(app: App)

    fun plus(module: MainMusicModule):MainMusicComponent

}