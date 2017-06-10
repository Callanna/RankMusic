package com.callanna.rankmusic.dagger.module

import android.content.Context
import android.util.Log
import com.callanna.rankmusic.api.MusicApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Callanna on 2017/5/26.
 */
@Module(includes = arrayOf(AppModule::class))
class ApiModule {
    @Provides fun provideRetrofit(baseUrl: HttpUrl, client: OkHttpClient, gson: Gson) =
            Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()

    @Provides fun provideBaseUrl() = HttpUrl.parse("http://ali-qqmusic.showapi.com/")
    @Provides fun provideOkhttp(context: Context, interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor ({
                    chain->
                    val request = chain.request().newBuilder()
                            .addHeader("Authorization", "APPCODE 6a4d0966c96742f3ac8da1864856658e")
                            .build()
                    chain.proceed(request)
                }).build()
    }
    @Provides fun provideInterceptor() : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor{
            msg -> Log.d("okhttp",msg)
        }
        interceptor.level =  HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides fun provideGson() = GsonBuilder().create()

    @Provides fun provideApi(retrofit: Retrofit) = retrofit.create(MusicApi::class.java)
}

