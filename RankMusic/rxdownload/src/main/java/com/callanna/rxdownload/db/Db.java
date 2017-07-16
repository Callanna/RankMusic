package com.callanna.rxdownload.db;

import android.database.Cursor;

/**
 * Created by Callanna on 2017/7/16.
 */

public class Db {
    public static final int VERSION = 1;
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;
    public static final String DBNAME = "download.db";
    static final class DownLoadTable {
        static final String TABLE_NAME = "tb_download";

        static final String COLUMN_ID = "id";
        static final String COLUMN_URL = "url";
        static final String COLUMN_SAVE_NAME = "save_name";
        static final String COLUMN_SAVE_PATH = "save_path";
        static final String COLUMN_DOWNLOAD_SIZE = "download_size";
        static final String COLUMN_TOTAL_SIZE = "total_size";
        static final String COLUMN_DOWNLOAD_FLAG = "download_flag";
        static final String COLUMN_LastModify = "lastmodify";
        static final String COLUMN_RANGE = "range";
        public static final String COLUMN_CHENGED = "ischanged";
        static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_URL + " TEXT NOT NULL," +
                        COLUMN_SAVE_NAME + " TEXT," +
                        COLUMN_SAVE_PATH + " TEXT," +
                        COLUMN_LastModify + " TEXT," +
                        COLUMN_TOTAL_SIZE + " INTEGER," +
                        COLUMN_DOWNLOAD_SIZE + " INTEGER," +
                        COLUMN_RANGE + " INTEGER, "+
                        COLUMN_CHENGED + " INTEGER, "+
                        COLUMN_DOWNLOAD_FLAG + " INTEGER " +
                        " )";



    }
    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    private Db() {
        throw new AssertionError("No instances.");
    }
}
