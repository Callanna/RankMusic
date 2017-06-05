package com.callanna.rankmusic.util

/**
 * Created by Callanna on 2017/6/4.
 */
class Constants{
    //榜行榜id 3=欧美 5=内地 6=港台 16=韩国 17=日本 18=民谣 19=摇滚 23=销量 26=热歌
    companion object {
        val HOT_SONG = "26"
        val ROCK = "19"
        val UK = "3"
        val KOREA = "16"
        val LOCAL = "5"
        val ALL = "all"

        val MODE_ORDER = 0
        val MODE_LOOP = 1
        val MODE_REPEAT = 2
    }
}
