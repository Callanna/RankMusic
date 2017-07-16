package com.callanna.rxdownload.db;

import android.content.Context;
import android.util.Log;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Callanna on 2017/7/16.
 */

public class DBManager {
    private static final String QUERY_URL =
            "SELECT *  FROM " + Db.DownLoadTable.TABLE_NAME + " WHERE " + Db.DownLoadTable.COLUMN_URL + " = ?";

    private static final String QUERY_ALL =
            "SELECT *  FROM " + Db.DownLoadTable.TABLE_NAME ;

    private static final String QUERY_STATUS =
            "SELECT *  FROM " + Db.DownLoadTable.TABLE_NAME + " WHERE " + Db.DownLoadTable.COLUMN_DOWNLOAD_FLAG + " = ?";

    private volatile static DBManager singleton;
    private final Object databaseLock = new Object();
    private DBHelper mDbOpenHelper;
    private BriteDatabase db;
    private SqlBrite sqlBrite;



    private DBManager(Context context) {
        mDbOpenHelper = new DBHelper(context);
        sqlBrite =  new SqlBrite.Builder()
                .logger(new SqlBrite.Logger() {
                    @Override public void log(String message) {
                        Log.v("DataBase",message);
                    }
                })
                .build();
        db = sqlBrite.wrapDatabaseHelper(mDbOpenHelper, Schedulers.io());
        db.setLoggingEnabled(true);
    }

    public static DBManager getSingleton(Context context) {
        if (singleton == null) {
            synchronized (DBManager.class) {
                if (singleton == null) {
                    singleton = new DBManager(context);
                }
            }
        }
        return singleton;
    }

    public Observable<List<DownLoadBean>> searchDownloadByAll(){
       return db.createQuery(Db.DownLoadTable.TABLE_NAME,QUERY_ALL)
                .mapToList(DownLoadBean.MAPPER);
    }
    public Observable<List<DownLoadBean>>  searchDownloadByStatus(int status){
        return db.createQuery(Db.DownLoadTable.TABLE_NAME,QUERY_STATUS, String.valueOf(status))
                .mapToList(DownLoadBean.MAPPER);
    }
    public Observable< DownLoadBean>  searchDownloadByUrl(String url){
        return db.createQuery(Db.DownLoadTable.TABLE_NAME,QUERY_URL,url)
                .mapToOne(DownLoadBean.MAPPER);
    }

    public void add(DownLoadBean bean){
       db.insert(Db.DownLoadTable.TABLE_NAME,new DownLoadBean.Builder().get(bean).build());
    }

    public void update(DownLoadBean bean){
        db.update(Db.DownLoadTable.TABLE_NAME,new DownLoadBean.Builder().get(bean).build(),
                Db.DownLoadTable.COLUMN_ID +" = ? ", String.valueOf(bean.getId()));
    }
    public void updateStatusByUrl(String url, int flag){
        db.update(Db.DownLoadTable.TABLE_NAME,new DownLoadBean.Builder().status(flag).build(),
                Db.DownLoadTable.COLUMN_URL +" = ? ",url);
    }
    public void updateStatusByUrl(String url, DownLoadStatus flag){
        db.update(Db.DownLoadTable.TABLE_NAME,new DownLoadBean.Builder()
                        .status(flag.getStatus())
                        .downSize((int) flag.getDownloadSize())
                        .totalSize((int) flag.getTotalSize()).build(),
                Db.DownLoadTable.COLUMN_URL +" = ? ",url);
    }
    public void delete(String url){
        db.delete(Db.DownLoadTable.TABLE_NAME, Db.DownLoadTable.COLUMN_URL +" = ? ",url);
    }

    public void deleteWaiting(){
        db.delete(Db.DownLoadTable.TABLE_NAME, Db.DownLoadTable.COLUMN_DOWNLOAD_FLAG +" != ? ", String.valueOf(DownLoadStatus.COMPLETED));
    }

    public boolean recordNotExists(String url) {
       Long count = searchDownloadByUrl(url).observeOn(Schedulers.newThread())
                .count().blockingGet();
        return count>0;
    }
}
