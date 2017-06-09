package com.callanna.rankmusic.api

import com.callanna.rankmusic.bean.*
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Callanna on 2017/5/26.
 */
interface MusicApi{
    /**
     * 热门榜单  榜行榜id 3=欧美 5=内地 6=港台 16=韩国 17=日本 18=民谣 19=摇滚 23=销量 26=热歌
     */
    @GET("top")
    fun getRankByID(@Query("topid") page:String): Observable<JsonResult<List<Music>>>

    @GET("song-word")
    fun getSongWord(@Query("musicid") musicid:String):Observable<JsonSWResult>

    @GET("search")
    fun search(@Query("keyword") keyword:String,@Query("page") page:String = "0"):Observable<JsonSearch<List<Song>>>
}