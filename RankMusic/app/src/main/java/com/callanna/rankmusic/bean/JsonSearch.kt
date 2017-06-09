package com.callanna.rankmusic.bean

/**
 * Created by Callanna on 2017/6/2.
 */
class JsonSearch<T>(val showapi_res_code :Int,
                    val showapi_res_error:String,
                    val showapi_res_body:JsonSearchBody<T>){
    fun getSongList():T{
        return showapi_res_body.pagebean.contentlist;
    }

}


class JsonSearchBody<T>(val ret_code:Int, val pagebean:SearchPagebean<T>)
class SearchPagebean<T>(val contentlist:T,
                  val w:String,
                  val ret_code:Int,
                  val notice:String,
                  val maxResult:Int,
                  val allNum:Int,
                  val currentPage:Int,
                  val allPages:Int)