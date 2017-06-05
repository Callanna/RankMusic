package com.callanna.rankmusic.bean

/**
 * Created by Callanna on 2017/6/2.
 */
//"m4a": "http://ws.stream.qqmusic.qq.com/4829067.m4a?fromtag=46",
//"media_mid": "000buMWp48yjTi",
//"songid": 4829067,
//"singerid": 2,
//"albumname": "华纳超极品音色系列",
//"downUrl": "http://dl.stream.qqmusic.qq.com/4829067.mp3?vkey=5BE5A5FDE47DFCF49A1060C47C9B1B6F3CA3452956888026FE918C8E3FDE573774E49C0D18B4C8A5E96B8BD9989586F654DC0B781083084D&guid=2718671044",
//"singername": "BEYOND",
//"songname": "海阔天空",
//"albummid": "003kZ85M1cfaEF",
//"songmid": "000buMWp48yjTi",
//"albumpic_big": "http://i.gtimg.cn/music/photo/mid_album_300/E/F/003kZ85M1cfaEF.jpg",
//"albumpic_small": "http://i.gtimg.cn/music/photo/mid_album_90/E/F/003kZ85M1cfaEF.jpg",
//"albumid": 63074

data class Song(
        val media_mid:String,
        val songname :String,
        val seconds:Int,
        val albumname:String,
        val songid:Int,
        val singerid:Int,
        val albumpic_big:String,
        val albumpic_small:String,
        val albummid:String,
        val downUrl:String,
        val m4a:String,
        val singername:String,
        val albumid:Int
)