package com.callanna.rankmusic.mvp.model

import android.util.Log
import com.callanna.rankmusic.api.MusicApi
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.bean.SongLrc
import com.callanna.rankmusic.db.MusicDBManager
import com.callanna.rankmusic.mvp.contract.MainContract
import com.callanna.rankmusic.util.Constants
import rx.Observable
import javax.inject.Inject

/**
 * Created by Callanna on 2017/6/4.
 */
class MainModel
@Inject constructor(private val api:MusicApi):MainContract.Model{
    override fun searchByKey(key: String): Observable<List<Music>> {
        return  api.search(key).map({t ->
            var songs = t.getSongList()
            var musics =  ArrayList<Music>()
            for(item in songs){
               musics.add(Music(item.songname,item.seconds,item.albummid,item.songid,item.singerid,item.albumpic_big,
                       item.albumpic_small,item.downUrl,item.m4a,item.singername,item.albumid))
            }
            return@map musics
        })

    }

    override fun getSongLrc(songId: String):Observable<SongLrc> {
        var songLrc = MusicDBManager.instance.selectById(songId)
        if(!songLrc?.lrc.equals("")){
            return  Observable.just(SongLrc(songId,songLrc?.lrc.toString(),""))
        }else{
            Log.d("duanyl","getSongLrc api")
            return api.getSongWord(songId).map({t-> MusicDBManager.instance.saveLrc(songId,t.getSongLrc(songId).lyric);return@map t.getSongLrc(songId)})
        }
    }

    override fun getData(type: String): Observable<List<Music>> {
        var muiscs = MusicDBManager.instance.selectByType(type)
        if(muiscs.size >0){
            return  Observable.just(muiscs)
        }else{
            Log.d("duanyl","getData api")
            return api.getRankByID(type).map({t -> MusicDBManager.instance.addMusicList(type,t.getSongLista()) ;return@map t.getSongLista()})
        }
    }
}