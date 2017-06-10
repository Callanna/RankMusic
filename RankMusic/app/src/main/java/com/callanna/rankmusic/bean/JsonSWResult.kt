package com.callanna.rankmusic.bean

import android.util.Log

/**
 * Created by Callanna on 2017/6/2.
 */
class JsonSWResult(val showapi_res_code :Int,
                    val showapi_res_error:String,
                    val showapi_res_body:JsonSWBody ){
    fun getSongLrc(id:String):SongLrc{
        return SongLrc(id,asciiToString(showapi_res_body.lyric),showapi_res_body.lyric_txt)
    }
    private fun asciiToString(lyric: String): String {
        //歌词里有ASCII 代码  先用这种方式转换一下
        var temp = lyric.replace("&#32;","").replace("&#38;","&").replace("&#39;","'").replace("&#58;",":").replace("&#46;",".")
                .replace("&#45;","-").replace("&#10;","\n").replace("&#13;","\r").replace("&#40;","(").replace("&#41;",")")
        Log.d("duanyl","asciiToString "+temp)
        return temp
    }
}

class JsonSWBody(val ret_code:Int, val lyric:String,val lyric_txt:String)