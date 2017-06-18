package com.callanna.rankmusic.util

import android.media.MediaPlayer
import android.util.Log
import java.util.*

/**
 * Created by Callanna on 2017/6/5.
 */
class MediaPlayerUtil {
    protected lateinit var mediaplayer: MediaPlayer
    protected lateinit var mTimer: Timer
    protected lateinit var timetask: TimerTask
    private   var mUrl = ""
    lateinit var playStateChange: PlayStateChangeListener

    init {
        mediaplayer = MediaPlayer()
        mediaplayer.setOnErrorListener({ mp, what, extra ->
            Log.d("duanyl", "what:" + what)
            false
        })
        mediaplayer.setOnCompletionListener {
            // mediaplayer.reset()
            playStateChange!!.onComplete()
        }

        mediaplayer.setOnPreparedListener {
            mediaplayer!!.start()
            playStateChange!!.onStart()
        }
       initTimer()
    }

    fun initTimer() {
        mTimer = Timer()
        timetask = object : TimerTask() {
            override fun run() {
                if (mediaplayer!!.isPlaying) {
                    val position = mediaplayer!!.getCurrentPosition()
                    val duration = mediaplayer!!.getDuration()
                    if (playStateChange != null) {
                        playStateChange!!.onChangeTime(position , duration)
                    }
                }
            }
        }
    }

    fun stopTimer(){
        if(mTimer != null){
            mTimer.cancel()
        }
        if(timetask != null){
            timetask.cancel()
        }
    }

    fun play(url: String) {
        Log.d("duanyl","url:"+url)
        mUrl = url
        if(mediaplayer!!.isPlaying){
            mediaplayer!!.stop()
        }
        mediaplayer!!.reset()
        mediaplayer!!.setDataSource(url)
        mediaplayer!!.prepareAsync()
        stopTimer()
        initTimer()
        mTimer!!.schedule(timetask, 0, 1000)

    }

    fun start() {
        mediaplayer!!.start()
    }

    fun pause() {
        mediaplayer!!.pause()
        playStateChange!!.onStop()
    }

    fun stop() {
        mediaplayer!!.stop()
        playStateChange!!.onStop()
    }

    fun seekTo(time: Int) {
        mediaplayer!!.seekTo(time)
    }

    fun getPlayUrl(): String {
        return mUrl
    }

    fun isPlaying(): Boolean {
        return mediaplayer!!.isPlaying
    }

    companion object {
        var instance: MediaPlayerUtil = MediaPlayerUtil()
    }

    interface PlayStateChangeListener {
        fun onStart()
        fun onStop()
        fun onComplete()
        fun onChangeTime(now: Int, total: Int)
    }
}