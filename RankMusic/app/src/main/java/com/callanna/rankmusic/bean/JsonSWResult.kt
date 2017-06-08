package com.callanna.rankmusic.bean

/**
 * Created by Callanna on 2017/6/2.
 */
class JsonSWResult(val showapi_res_code :Int,
                    val showapi_res_error:String,
                    val showapi_res_body:JsonSWBody ){
    fun getSongLrc(id:String):SongLrc{
        return SongLrc(id,showapi_res_body.lyric,showapi_res_body.lyric_txt)
    }
}


class JsonSWBody(val ret_code:Int, val lyric:String,val lyric_txt:String)