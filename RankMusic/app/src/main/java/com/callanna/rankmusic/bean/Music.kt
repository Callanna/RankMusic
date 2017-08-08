package com.callanna.rankmusic.bean

/**
 * Created by Callanna on 2017/5/26.
 */
data class Music(
        val songname :String,
        val seconds:Int,
        val albummid:String,
        val songid:Int,
        val singerid:Int,
        val albumpic_big:String,
        val albumpic_small:String,
        val downUrl:String,
        val url:String,
        val singername:String,
        val albumid:Int
)

class MusicMap(var map: MutableMap<String, Any?>){
    var songname :String by map
    var seconds:Int by map
    var albummid:String by map
    var songid:Int by map
    var singerid:Int by map
    var albumpic_big:String by map
    var albumpic_small:String by map
    var downUrl:String by map
    var url:String by map
    var singername:String by map
    var albumid:Int by map
    var type:String by map
    var lrc:String by map
    var lastPlayTime:Int by map
    var isLove:Int by map
    var downLoadType:String by map

    constructor(
            songname :String,
            seconds:Int,
            albummid:String,
            songid:Int,
            singerid:Int,
            albumpic_big:String,
            albumpic_small:String,
            downUrl:String,
            url:String,
            singername:String,
            albumid:Int
    ):this(HashMap()){
        this.songname = songname
        this.seconds = seconds
        this.albummid = albummid
        this.songid = songid
        this.singerid = singerid
        this.albumpic_big = albumpic_big
        this.albumpic_small = albumpic_small
        this.downUrl = downUrl
        this.url = url
        this.singername = singername
        this.albumid = albumid
    }
   fun toMusic():Music = Music(this.songname,this.seconds,this.albummid,this.songid,this.singerid,
           this.albumpic_big,this.albumpic_small,this.downUrl,this.url,this.singername,this.albumid)
}
