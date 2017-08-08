package com.callanna.rankmusic.mvp.presenter

import android.util.Log
import android.widget.Toast
import com.callanna.rankmusic.App
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.db.MusicDBManager
import com.callanna.rankmusic.mvp.BasePresenter
import com.callanna.rankmusic.mvp.contract.PlayContract
import com.callanna.rankmusic.mvp.model.MainModel
import com.callanna.rankmusic.util.Constants
import com.callanna.rankmusic.util.Logd
import com.callanna.rankmusic.util.MediaPlayerUtil
import rx.android.schedulers.AndroidSchedulers
import zlc.season.rxdownload2.RxDownload
import javax.inject.Inject

/**
 * Created by Callanna on 2017/6/5.
 */
class PlayPresenter
@Inject constructor(private val mModel: MainModel,
                    private val mView: PlayContract.View): PlayContract.Presenter, BasePresenter() {


    private val songlist:ArrayList<Music> = ArrayList()
    private var currentPosition:Int = 0
    private var currentMode:Int = Constants.MODE_ORDER
    private var currentType:String = ""

    init {
        App.instance.Logd("PlayPresenter  init")
        MediaPlayerUtil.instance.playStateChange = object : MediaPlayerUtil.PlayStateChangeListener{
            override fun onStart() {
                Log.d("duanyl","onStart")
                mView.start()
            }

            override fun onStop() {
                Log.d("duanyl","onStop")
                mView.stop()
            }

            override fun onComplete() {
                Log.d("duanyl","onComplete")
                 when(currentMode){
                     Constants.MODE_ORDER->
                             next()
                     Constants.MODE_LOOP->
                             play((songlist.size * Math.random()).toInt())
                     Constants.MODE_REPEAT->
                            play(currentPosition)
                 }
            }

            override fun onChangeTime(now: Int, total: Int) {
                Log.d("duanyl","onChangeTime")
                mView.currentPlayTime(now /1000 ,total/1000 )
            }

        }
    }
    override fun play(position: Int,url: String) {
        Log.d("duanyl","url  + :"+url + "pos :"+position)
        if(songlist.size <= position){
            return
        }
        if( url.equals("")) {
            MediaPlayerUtil.instance.play(songlist[position].url)
        }else{
            MediaPlayerUtil.instance.play(url)
        }
        getSongLrc(songlist[position].songid.toString())
        App.instance.currentType = currentType
        App.instance.currentSong= songlist[position]
        mView.setCurrentSong(songlist[position])
        mView.setCheckLove(MusicDBManager.instance.isLove(songlist[position].songid.toString()))
        App.instance.currentPosition = position
        MusicDBManager.instance.saveLastPlayTime(App.instance.currentSong!!.songid.toString())
    }
    override fun seekTo(time:Int) {
        MediaPlayerUtil.instance.seekTo(time * 1000)
    }

    override fun getSongList(type: String) {
        currentType = type

        addSubscription(  when (type) {
            Constants.DownLoad ->
                mModel.getDownLoaded()
            Constants.DownLoadING ->
                mModel.getDownLoading()
            Constants.MyLove ->
                mModel.getMyLove()
            Constants.MyMUSIC ->
                mModel.getMyMusic()
            Constants.History ->
                mModel.getHistory()
            else ->
                mModel.getData(type)

        }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->
                    songlist.addAll(res)
                    Log.d("duanyl","duanyl=====>songlist ")
                    mView.setSongList(res)
                }, { e -> Log.e("duanyl", "error MainMusic:" + e.message) }))
    }
    override fun getSongLrc(songId: String) {
        addSubscription(mModel.getSongLrc(songId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->
                   mView.setSongLrc(res.lyric)
                }, { e -> Log.e("duanyl", "error MainMusic:" + e.message) }))
    }
    override fun searchByKey(key: String) {
        addSubscription(mModel.searchByKey(key).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->
                    mView.setSongList(res)
                }, { e -> Log.e("duanyl", "error MainMusic:" + e.message) }))
    }


    override fun stop() {
        MediaPlayerUtil.instance.stop()
    }
    override fun pause() {
        MediaPlayerUtil.instance.pause()
    }
    override fun start() {
        MediaPlayerUtil.instance.start()
    }
    override fun setLove(checked: Boolean) {
       MusicDBManager.instance.savelove(App.instance.currentSong!!.songid.toString(),checked)
    }

    override fun next() {
        if(currentPosition >= 0 && currentPosition < songlist.size-1) {
            currentPosition++
        }else{
            currentPosition = 0
        }
        play(currentPosition)
    }

    override fun pre() {
        if(currentPosition >  0 && currentPosition < songlist.size) {
            currentPosition--
        }else{
            currentPosition = songlist.size -1
        }
        play(currentPosition)
    }

    override fun setMode(mode: Int) {
         currentMode = mode
         mView.setMode(currentMode)
    }
    override fun changeMode() {
         currentMode++
        if(currentMode > 2){
            currentMode = 0
        }
        setMode(currentMode)
    }
    override fun download() {
        Log.d("duanyl","download url:"+App.instance.currentSong!!.downUrl)
        MusicDBManager.instance.saveDownLoad( App.instance.currentSong!!.songid.toString(),Constants.DownLoadING)
        RxDownload.getInstance(App.instance)
                .maxThread(3)                     //设置最大线程
                .maxRetryCount(3)                 //设置下载失败重试次数
                .maxDownloadNumber(5)
                .serviceDownload(App.instance.currentSong!!.downUrl)
                .subscribe {
                    Toast.makeText(App.instance, "开始下载", Toast.LENGTH_SHORT).show()
                }
    }

}