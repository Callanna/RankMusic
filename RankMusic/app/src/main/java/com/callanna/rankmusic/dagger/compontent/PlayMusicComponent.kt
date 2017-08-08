
package com.callanna.rankmusic.dagger.compontent

import com.callanna.rankmusic.mvp.contract.PlayContract
import com.callanna.rankmusic.ui.activity.PlayActivity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by Callanna on 2017/6/6.
 */
@Subcomponent(modules = arrayOf(PlayMusicModule::class)) interface PlayMusicComponent{
    fun inject(act: PlayActivity)
}
@Module class PlayMusicModule(private val mView: PlayContract.View){
    @Provides fun getView()=mView
}