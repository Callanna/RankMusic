package com.callanna.rankmusic.mvp.contract

import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.util.Constants
import rx.Observable

/**
 * Created by Callanna on 2017/6/4.
 */
interface MainContract{
    interface View{
        fun setHotSong(result: List<Music>)
        fun setRock(result: List<Music>)
        fun setLocal(result: List<Music>)
        fun setUK(result: List<Music>)
        fun setKorea(result: List<Music>)
    }
    interface Model{
        fun getData(type :String):Observable<List<Music>>
    }

    interface Presenter{
        fun getData(type :String = Constants.ALL)
    }
}