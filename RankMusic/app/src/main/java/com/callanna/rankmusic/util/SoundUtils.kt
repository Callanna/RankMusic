package com.callanna.rankmusic.util

import android.content.Context
import android.media.AudioManager
import android.util.Log

/**
 * 音量相关工具类
 */
class SoundUtils private constructor(context: Context) {
    private val audiomanage: AudioManager

    init {
        this.audiomanage = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    val soundMax: Int
        get() = this.audiomanage.getStreamMaxVolume(1)

    val soundValue: Int
        get() = this.audiomanage.getStreamVolume(1)

    fun setStreamVolume(index: Int) {
        this.audiomanage.setStreamVolume(AudioManager.STREAM_SYSTEM, index, 0)
    }

    /**
     * 设置静音模式

     * @param isOpen
     */
    fun setSilentMode(isOpen: Boolean) {
        if (isOpen) {
            Log.d("SoundUtils", "soundValue:" + soundValue)
            lastsoundValue = soundValue
            setStreamVolume(0)
        } else {
            setStreamVolume(lastsoundValue)
        }
    }

    val isSilence: Boolean
        get() = soundValue == 0

    companion object {
        fun getInstance(paramContext: Context): SoundUtils {
            var instance = SoundUtils(paramContext)
            return instance
        }
        private var lastsoundValue: Int = 0
    }
}