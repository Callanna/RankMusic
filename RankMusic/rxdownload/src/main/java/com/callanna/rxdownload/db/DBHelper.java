package com.callanna.rxdownload.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Callanna on 2017/7/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context,Db.DBNAME, null /* factory */, Db.VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(Db.DownLoadTable.CREATE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
