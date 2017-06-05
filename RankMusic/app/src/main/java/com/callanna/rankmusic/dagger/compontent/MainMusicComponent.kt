package com.callanna.rankmusic.dagger.compontent

import com.callanna.rankmusic.mvp.contract.MainContract
import com.callanna.rankmusic.ui.activity.MainActivity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by Callanna on 2017/6/4.
 */
@Subcomponent(modules = arrayOf(MainMusicModule::class)) interface MainMusicComponent{
    fun inject(act: MainActivity)
}
@Module class MainMusicModule(private val mView:MainContract.View){
    @Provides fun getView()=mView
}
