package com.callanna.rankmusic.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.callanna.rankmusic.bean.Music
import java.util.*

/**
 * Created by Callanna on 2017/8/7.
 */
class MediaUtils{
    init {
        val ms = ArrayList<Music>()
    }

    companion object {
        fun getAllMediaList(context: Context ): List<Music> {
            var cursor: Cursor? = null
            var mediaList: MutableList<Music> = ArrayList()
            try {
                cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, MediaStore.Audio.Media.IS_MUSIC)
                if (cursor == null) {
                    Log.d(ContentValues.TAG, "The getMediaList cursor is null.")
                    return mediaList
                }
                val count = cursor.count
                if (count <= 0) {
                    Log.d(ContentValues.TAG, "The getMediaList cursor count is 0.")
                    return mediaList
                }
                mediaList = ArrayList<Music>()
                var mediaEntity: Music? = null
                //          String[] columns = cursor.getColumnNames();
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val album_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                    //歌曲的名称
                    val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    //歌曲的专辑名
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    //歌曲文件的名称
                    val display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    //歌曲的总播放时长
                    val duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    //歌曲文件的大小
                    val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                    val durationStr = longToStrTime(duration)

                    if (!checkIsMusic(duration, size)) {
                        continue
                    }
                    //歌曲的歌手名
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    // 歌曲文件的全路径
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val img = getAlbumArt(context, album_id)
                    mediaEntity = Music(title, duration, id.toString() + "", -1, -1, img, img, "", path, artist, Integer.parseInt(album_id))
                    mediaList.add(mediaEntity)
                }
            } catch (e: Exception) {

            } finally {
                if (cursor != null) {
                    cursor.close()
                }
            }
            return mediaList
        }

        /**
         * 根据时间和大小，来判断所筛选的media 是否为音乐文件，具体规则为筛选小于30秒和1m一下的
         */
        fun checkIsMusic(time: Int, size: Long): Boolean {
            var time = time
            if (time <= 0 || size <= 0) {
                return false
            }

            time /= 1000
            var minute = time / 60
            //  int hour = minute / 60;
            val second = time % 60
            minute %= 60
            if (minute <= 0 && second <= 30) {
                return false
            }
            if (size <= 1024 * 1024) {
                return false
            }
            return true
        }

        /**
         * 定义一个方法用来格式化获取到的时间
         */
        fun longToStrTime(time: Int): String {
            if (time / 1000 % 60 < 10) {
                return (time / 1000 / 60).toString() + ":0" + time / 1000 % 60

            } else {
                return (time / 1000 / 60).toString() + ":" + time / 1000 % 60
            }

        }

        private fun getAlbumArt(context: Context, album_id: String): String  {
            val mUriAlbums = "content://media/external/audio/albums"
            val projection = arrayOf("album_art")
            var cur = context.contentResolver.query(Uri.parse(mUriAlbums + "/" + album_id),
                    projection, null, null, null)
            var album_art: String = ""
            if (cur!!.count > 0 && cur.columnCount > 0) {
                cur.moveToNext()
                album_art = cur.getString(0)
            }
            cur.close()
            cur = null
            return album_art
        }
    }

}