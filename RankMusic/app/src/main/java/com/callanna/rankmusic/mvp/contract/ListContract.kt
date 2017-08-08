package com.callanna.rankmusic.mvp.contract

import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.util.Constants

/**
 * Created by Callanna on 2017/8/7.
 */
interface ListContract{
    interface View{
        fun setMyLove(result: List<Music>)
        fun setDownLoading(result: List<Music>)
        fun setDownLoad(result: List<Music>)
        fun setHistory(result: List<Music>)
        fun setMuMusic(result: List<Music>)
    }

    interface Presenter{
        fun getData(type :String = Constants.ALL)
    }
}