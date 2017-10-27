package com.callanna.rankmusic.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.SystemClock
import android.util.Log
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.bean.MusicMap
import com.ldm.kotlin.db.DBHelper
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.select
import java.util.*

/**
 * Created by Callanna on 2017/6/10.
 */
class MusicDBManager {
    companion object {
        val instance: MusicDBManager = MusicDBManager()

    }

    var dbHelper: DBHelper

    init {
        Log.d("duanyl", "init dbhelper");
        dbHelper = DBHelper()
    }

    public fun selectByType(type: String): List<Music> =
            dbHelper.writableDatabase.select(DBHelper.MusicTable.T_NAME)
            .whereSimple("${DBHelper.MusicTable.TYPE} = ?", type)
                    .orderBy(DBHelper.MusicTable.SECONDS,SqlOrderDirection.ASC)
            .parseList { MusicMap(HashMap(it)) }.map {
               it.toMusic()
            }

    public fun selectByAll(): List<Music> = dbHelper.writableDatabase.select(DBHelper.MusicTable.T_NAME)
            .parseList { MusicMap(HashMap(it)) }.map { it.toMusic() }

    public fun selectById(songId: String): MusicMap? = dbHelper.writableDatabase.select(DBHelper.MusicTable.T_NAME)
            .whereSimple("${DBHelper.MusicTable.SONGID} = ?", songId)
            .parseOpt { MusicMap(HashMap(it)) }

    public fun addMusicList(type: String, musics: List<Music>) {
        var value = ContentValues()
        for (item in musics) {
           Log.d("MusicDB" ,""+item.toString())
            value.put(DBHelper.MusicTable.SONGID, item.songid)
            value.put(DBHelper.MusicTable.SONGNAME, item.songname)
            value.put(DBHelper.MusicTable.SINGERID, item.singerid)
            value.put(DBHelper.MusicTable.SINGER_NAME, item.singername)
            value.put(DBHelper.MusicTable.SECONDS, item.seconds)
            value.put(DBHelper.MusicTable.ALBUMID, item.albumid)
            value.put(DBHelper.MusicTable.ALBUMMID, item.albummid)
            value.put(DBHelper.MusicTable.ALBUMPIC_BIG, item.albumpic_big)
            value.put(DBHelper.MusicTable.ALBUMPIC_SMALL, item.albumpic_small)
            value.put(DBHelper.MusicTable.DOWNURL, item.downUrl)
            value.put(DBHelper.MusicTable.URL, item.url)
            value.put(DBHelper.MusicTable.TYPE, type)
            value.put(DBHelper.MusicTable.LRC, "")
            value.put(DBHelper.MusicTable.DownLoadType, "")
            value.put(DBHelper.MusicTable.isLove, 0)
            value.put(DBHelper.MusicTable.LastPlayTime, 0)
            dbHelper.writableDatabase.insertWithOnConflict(DBHelper.MusicTable.T_NAME, null, value,SQLiteDatabase.CONFLICT_REPLACE)
        }
    }

    public fun addMusic(type: String, music: Music) {
        var value = ContentValues()
        value.put(DBHelper.MusicTable.SONGID, music.songid)
        value.put(DBHelper.MusicTable.SONGNAME, music.songname)
        value.put(DBHelper.MusicTable.SINGERID, music.singerid)
        value.put(DBHelper.MusicTable.SINGER_NAME, music.singername)
        value.put(DBHelper.MusicTable.SECONDS, music.seconds)
        value.put(DBHelper.MusicTable.ALBUMID, music.albumid)
        value.put(DBHelper.MusicTable.ALBUMMID, music.albummid)
        value.put(DBHelper.MusicTable.ALBUMPIC_BIG, music.albumpic_big)
        value.put(DBHelper.MusicTable.ALBUMPIC_SMALL, music.albumpic_small)
        value.put(DBHelper.MusicTable.DOWNURL, music.downUrl)
        value.put(DBHelper.MusicTable.URL, music.url)
        value.put(DBHelper.MusicTable.TYPE, type)
        value.put(DBHelper.MusicTable.LRC, "")
        value.put(DBHelper.MusicTable.DownLoadType, "")
        value.put(DBHelper.MusicTable.isLove, 0)
        value.put(DBHelper.MusicTable.LastPlayTime, 0)
        dbHelper.writableDatabase.insertWithOnConflict(DBHelper.MusicTable.T_NAME, null, value,SQLiteDatabase.CONFLICT_REPLACE)
    }

    public fun savelove(songId: String, love: Boolean) {
        var value = ContentValues()
        value.put(DBHelper.MusicTable.isLove, if (love) 1 else 0)
        dbHelper.writableDatabase.update(DBHelper.MusicTable.T_NAME, value, DBHelper.MusicTable.SONGID + " =?", arrayOf(songId))
    }

    public fun saveLastPlayTime(songId: String) {
        var value = ContentValues()
        value.put(DBHelper.MusicTable.LastPlayTime, SystemClock.currentThreadTimeMillis() / 1000)
        dbHelper.writableDatabase.update(DBHelper.MusicTable.T_NAME, value, DBHelper.MusicTable.SONGID + " =?", arrayOf(songId))
    }

    public fun saveDownLoad(songId: String, type: String) {
        var value = ContentValues()
        value.put(DBHelper.MusicTable.DownLoadType, type)
        dbHelper.writableDatabase.update(DBHelper.MusicTable.T_NAME, value, DBHelper.MusicTable.SONGID + " =?", arrayOf(songId))
    }

    public fun getMyLove(): List<Music> = dbHelper.writableDatabase.select(DBHelper.MusicTable.T_NAME)
            .whereSimple("${DBHelper.MusicTable.isLove} = ?", "1")
            .parseList { MusicMap(HashMap(it)) }.map { it.toMusic() }

    public fun getHistory(): List<Music> = dbHelper.writableDatabase.select(DBHelper.MusicTable.T_NAME)
            .whereSimple("${DBHelper.MusicTable.LastPlayTime} > ?", "0")
            .orderBy(DBHelper.MusicTable.LastPlayTime)
            .limit(50)
            .parseList { MusicMap(HashMap(it)) }.map { it.toMusic() }

    public fun getDownLoad(type: String): List<Music> = dbHelper.writableDatabase.select(DBHelper.MusicTable.T_NAME)
            .whereSimple("${DBHelper.MusicTable.DownLoadType} = ?", type)
            .parseList { MusicMap(HashMap(it)) }.map { it.toMusic() }


    fun isLove(songid: String): Boolean {
        var music = selectById(songid)
        return if (music!!.isLove == 1) true else false
    }

    public fun saveLrc(songId: String, lrc: String) {
        var value = ContentValues()
        value.put(DBHelper.MusicTable.LRC, lrc)
        dbHelper.writableDatabase.update(DBHelper.MusicTable.T_NAME, value, DBHelper.MusicTable.SONGID + " =?", arrayOf(songId))
        Log.d("duanyl", "saveLrc" + selectById(songId)?.lrc)
    }

    public fun deleteMusic(songId: String) {
        dbHelper.writableDatabase.delete(DBHelper.MusicTable.T_NAME, DBHelper.MusicTable.SONGID + " =?", arrayOf(songId))
    }

    //返回数据集合
    public fun <T : Any> SelectQueryBuilder.parseList(
            parser: (Map<String, Any>) -> T): List<T> =
            parseList(object : MapRowParser<T> {
                override fun parseRow(columns: Map<String, Any>): T = parser(columns)
            })

    //返回可以为null对象，parseSingle效果一样，但是返回不能为null
    public fun <T : Any> SelectQueryBuilder.parseOpt(
            parser: (Map<String, Any>) -> T): T? =
            parseOpt(object : MapRowParser<T> {
                override fun parseRow(columns: Map<String, Any>): T = parser(columns)
            })

}
