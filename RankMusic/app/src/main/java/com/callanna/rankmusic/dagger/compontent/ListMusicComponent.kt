
package com.callanna.rankmusic.dagger.compontent

import com.callanna.rankmusic.mvp.contract.ListContract
import com.callanna.rankmusic.ui.activity.ListActivity
import com.callanna.rankmusic.ui.fragment.mian.MeFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by Callanna on 2017/6/6.
 */
@Subcomponent(modules = arrayOf(ListMusicModule::class)) interface ListMusicComponent{
    fun inject(act: ListActivity)
    fun inject(act: MeFragment)
}
@Module class ListMusicModule(private val mView: ListContract.View){
    @Provides fun getView()=mView
}