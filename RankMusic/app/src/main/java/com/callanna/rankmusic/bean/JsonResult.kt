package com.callanna.rankmusic.bean

/**
 * Created by Callanna on 2017/5/26.
 */
class JsonResult<T>(val showapi_res_code :Int,
                    val showapi_res_error:String,
                    val showapi_res_body:JsonBody<T>){
    fun getSongLista():T{
        return showapi_res_body.pagebean.songlist;
    }
}


class JsonBody<T>(val ret_code:Int, val pagebean:Pagebean<T>)
class Pagebean<T>(val songlist:T,
                  val total_song_num:Int,
                  val ret_code:Int,
                  val update_time:String,
                  val color:Int,
                  val cur_song_num:Int,
                  val comment_num:Int,
                  val code:Int,
                  val currentPage:Int,
                  val song_begin:Int,
                  val totalpage:Int)