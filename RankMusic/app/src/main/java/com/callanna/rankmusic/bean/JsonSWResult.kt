package com.callanna.rankmusic.bean

/**
 * Created by Callanna on 2017/6/2.
 */
class JsonSWResult(val showapi_res_code :Int,
                    val showapi_res_error:String,
                    val showapi_res_body:JsonSWBody ){
    fun getlyricList():HashMap<String ,String>{
        val map :HashMap<String ,String> = HashMap()

        return map;
    }
}


class JsonSWBody(val ret_code:Int, val lyric:String,val lyric_txt:String)