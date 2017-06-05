package com.callanna.rankmusic.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Callanna on 2017/5/26.
 */
@Module
class AppModule(private val context: Context){
    @Provides fun provideContext() = context
}