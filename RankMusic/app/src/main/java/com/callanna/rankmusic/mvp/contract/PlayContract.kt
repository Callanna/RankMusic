package com.callanna.rankmusic.mvp.contract

import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.util.Constants
import rx.Observable

/**
 * Created by Callanna on 2017/6/5.
 */
interface PlayContract{
    interface View{
        fun setSongList(result: List<Music>)
        fun stop()
        fun start()
        fun currentPlayTime(now: Int,total:Int)
        fun setMode(mode :Int = Constants.MODE_ORDER)
        fun setCurrentSong(song:Music)
        fun setSongLrc(lrc:String)
    }
    interface Model{
        fun getSongList(type :String): Observable<List<Music>>
    }

    interface Presenter{
        fun getSongList(type :String = Constants.ALL)
        fun getSongLrc(songId:String)
        fun play(position:Int = 0,url:String = "")
        fun stop()
        fun pause()
        fun start()
        fun seekTo(time:Int)
        fun next()
        fun pre()
        fun setMode(mode:Int)
    }
}