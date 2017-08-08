package com.ldm.kotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.callanna.rankmusic.App
import org.jetbrains.anko.db.*

/**
 * Created by Callanna on 2017/6/10.
 */
class DBHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        DB_NAME, null, DB_VERSION) {
    //数据库相关字段
    companion object {
        val DB_NAME = "music.db"//数据库名
        val DB_VERSION = 1//版本
        val instance by lazy { DBHelper() }
    }

    //定义歌曲表字段
    object MusicTable {
        val T_NAME = "music"//数据表名称
        val SONGNAME = "songname"
        val SECONDS = "seconds"
        val ALBUMMID = "albummid"
        val SONGID = "songid"
        val SINGERID = "singerid"
        val ALBUMPIC_BIG = "albumpic_big"
        val ALBUMPIC_SMALL= "albumpic_small"
        val DOWNURL = "downUrl"
        val URL = "url"
        val SINGER_NAME = "singername"
        val ALBUMID = "albumid"
        val TYPE = "type"
        val LRC = "lrc"
        val LastPlayTime = "lastPlayTime"
        val isLove = "isLove"
        val DownLoadType = "downLoadType"
    }

    override fun onCreate(db: SQLiteDatabase) {
        //创建学生对应地表
        db.createTable(MusicTable.T_NAME, true,
                MusicTable.SONGID to INTEGER + PRIMARY_KEY,
                MusicTable.SONGNAME to TEXT,
                MusicTable.SECONDS to INTEGER,
                MusicTable.ALBUMMID to INTEGER,
                MusicTable.SINGERID to INTEGER,
                MusicTable.ALBUMPIC_BIG to TEXT,
                MusicTable.ALBUMPIC_SMALL to TEXT,
                MusicTable.DOWNURL to TEXT,
                MusicTable.URL to TEXT,
                MusicTable.SINGER_NAME to TEXT,
                MusicTable.ALBUMID to INTEGER,
                MusicTable.TYPE to TEXT,
                MusicTable.DownLoadType to TEXT,
                MusicTable.isLove to INTEGER,
                MusicTable.LastPlayTime to INTEGER,
                MusicTable.LRC to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //数据库升级时，先删除表再创建
        db.dropTable(MusicTable.T_NAME, true)
        onCreate(db)
    }

}
