package com.callanna.rankmusic.mvp.model

import com.callanna.rankmusic.api.MusicApi
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.mvp.contract.PlayContract
import com.callanna.rankmusic.util.Constants
import rx.Observable
import javax.inject.Inject

/**
 * Created by Callanna on 2017/6/5.
 */
class PlayModel
@Inject constructor(private val api: MusicApi): PlayContract.Model{
    override fun getSongList(type: String): Observable<List<Music>> {
        when(type){
            Constants.HOT_SONG ->
                return if(cache_hotsong.size <= 0)api.getRankByID(Constants.HOT_SONG).map({ t -> cache_hotsong.addAll(t.getSongLista()) ;return@map t.getSongLista()})
                else Observable.just(cache_hotsong)
            Constants.ROCK ->
                return if(cache_rock.size <= 0)api.getRankByID(Constants.ROCK).map({ t-> cache_rock.addAll(t.getSongLista()) ;return@map t.getSongLista()})
                else Observable.just(cache_rock)
            Constants.UK ->
                return if(cache_uk.size <= 0)api.getRankByID(Constants.UK).map({ t->cache_uk.addAll(t.getSongLista()) ;return@map t.getSongLista()})
                else Observable.just(cache_uk)
            Constants.LOCAL ->
                return if(cache_local.size <= 0)api.getRankByID(Constants.LOCAL).map({ t-> cache_local.addAll(t.getSongLista()) ;return@map t.getSongLista()})
                else Observable.just(cache_local)
            Constants.KOREA ->
                return if(cache_korea.size <= 0)api.getRankByID(Constants.KOREA).map({ t-> cache_korea.addAll(t.getSongLista()) ;return@map t.getSongLista()})
                else Observable.just(cache_korea)
            else ->
                return if(cache_hotsong.size <= 0)api.getRankByID(Constants.HOT_SONG).map({ t ->cache_hotsong.addAll(t.getSongLista()) ; return@map t.getSongLista()})
                else Observable.just(cache_hotsong)

        }
    }
    //
    companion object {
        var cache_hotsong: ArrayList<Music> = ArrayList()
        var cache_rock: ArrayList<Music> = ArrayList()
        var cache_uk: ArrayList<Music> = ArrayList()
        var cache_local: ArrayList<Music> = ArrayList()
        var cache_korea: ArrayList<Music> = ArrayList()
    }

}