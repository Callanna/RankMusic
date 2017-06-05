package com.callanna.rankmusic.util

import android.media.MediaPlayer
import android.util.Log
import java.util.*

/**
 * Created by Callanna on 2017/6/5.
 */
class MediaPlayerUtil{
    protected lateinit var mediaplayer: MediaPlayer
    protected lateinit var mTimer: Timer
    protected lateinit var timetask:TimerTask
    lateinit var playStateChange:PlayStateChangeListener

    init {
        MediaPlayerUtil.instance = this
        mediaplayer = MediaPlayer()
        mediaplayer.setOnErrorListener({ mp, what, extra ->
            Log.d("duanyl", "what:" + what)
            false
        })
        mediaplayer.setOnCompletionListener {
            playStateChange!!.onComplete()
        }

        mediaplayer.setOnPreparedListener {
            mediaplayer!!.start()
            playStateChange!!.onStart()
        }

        mTimer = Timer()
        timetask = object : TimerTask() {
            override fun run() {
                if(mediaplayer!!.isPlaying){
                    val position = mediaplayer!!.getCurrentPosition()
                    val duration = mediaplayer!!.getDuration()
                    if(playStateChange!= null){
                        playStateChange!!.onChangeTime(position,duration)
                    }
                }
            }
        }
    }

    fun play(url:String){
        mediaplayer!!.setDataSource(url)
        mediaplayer!!.prepareAsync()
        mTimer!!.schedule(timetask,0,1000)
    }
    fun start(){
        mediaplayer!!.start()
    }

    fun puase(){
        mediaplayer!!.pause()
        playStateChange!!.onStop()
    }
    fun stop(){
        mediaplayer!!.stop()
        playStateChange!!.onStop()
    }
    fun seekTo( time:Int){
        mediaplayer!!.seekTo(time)
    }
    companion object{
        lateinit var instance: MediaPlayerUtil
    }
    interface PlayStateChangeListener{
        fun onStart()
        fun onStop()
        fun onComplete()
        fun onChangeTime(now:Int,total:Int)
    }
}