package com.callanna.rxdownload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.callanna.rxdownload.api.DownloadHelper;
import com.callanna.rxdownload.db.DBManager;
import com.callanna.rxdownload.db.DownLoadBean;
import com.callanna.rxdownload.db.DownLoadStatus;

import java.io.File;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static com.callanna.rxdownload.Utils.log;

/**
 * Created by Callanna on 2017/7/16.
 */

public class RxDownLoad {
    private static final Object object = new Object();
    @SuppressLint("StaticFieldLeak")
    private volatile static RxDownLoad instance;
    private volatile static boolean bound = false;

    static {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof InterruptedException) {
                    log("Thread interrupted");
                } else if (throwable instanceof InterruptedIOException) {
                    log("Io interrupted");
                } else if (throwable instanceof SocketException) {
                    log("Socket error");
                }
            }
        });
    }

    private int maxDownloadNumber = 3;

    private Context context;
    private Semaphore semaphore;
    private DownloadHelper downloadHelper;

    private RxDownLoad(Context context) {
        this.context = context.getApplicationContext();
        downloadHelper = new DownloadHelper(context);
        semaphore = new Semaphore(maxDownloadNumber);
    }

    /**
     * Return RxDownload Instance
     *
     * @param context context
     * @return RxDownload
     */
    public static RxDownLoad getInstance(Context context) {
        if (instance == null) {
            synchronized (RxDownLoad.class) {
                if (instance == null) {
                    instance = new RxDownLoad(context);
                }
            }
        }
        return instance;
    }

    /**
     * get Files by url. May be NULL if this url record not exists.
     * File[] {DownloadFile, TempFile, LastModifyFile}
     *
     * @param url url
     * @return Files
     */
    @Nullable
    public File[] getRealFiles(String url) {
        return downloadHelper.getFiles(url);
    }

    /**
     * get Files by saveName and savePath.
     *
     * @param saveName saveName
     * @param savePath savePath
     * @return Files
     */
    public File[] getRealFiles(String saveName, String savePath) {
        return Utils.getFiles(saveName, savePath);
    }

    /**
     * set default save path.
     *
     * @param savePath default save path.
     * @return instance.
     */
    public RxDownLoad defaultSavePath(String savePath) {
        downloadHelper.setDefaultSavePath(savePath);
        return this;
    }

    /**
     * set max thread to download file.
     *
     * @param max max threads
     * @return instance
     */
    public RxDownLoad maxThread(int max) {
        downloadHelper.setMaxThreads(max);
        return this;
    }


    /**
     * set max download number when service download
     *
     * @param max max download number
     * @return instance
     */
    public RxDownLoad maxDownloadNumber(int max) {
        this.maxDownloadNumber = max;
        semaphore = new Semaphore(maxDownloadNumber);
        return this;
    }

    private Map<String, Disposable> disposableMap;
    private boolean isStopAll = false;

    public void download(final String url) throws InterruptedException {
        if (disposableMap == null) {
            disposableMap = new ConcurrentHashMap();
        }
        DownLoadBean bean = downloadHelper.prepare(url);
        if (isStopAll) {
            return;
        }
        semaphore.acquire();
        if (isStopAll) {
            semaphore.release();
        }
        final Disposable disposable = downloadHelper.startDownLoad(bean)
                .subscribeOn(Schedulers.io())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        log("finally and release...");
                        if (disposableMap.size() > 0) {
                            disposableMap.remove(url);
                        }
                        semaphore.release();
                    }
                }).subscribe();
        disposableMap.put(url, disposable);
    }

    private void startOtherWaiting() {
        DBManager.getSingleton(context).searchDownloadByStatus(DownLoadStatus.WAITING).flatMap(new Function<List<DownLoadBean>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull List<DownLoadBean> downLoadBeen) throws Exception {
                if (downLoadBeen.size() > 0) {
                    return null;
                }
                for (DownLoadBean bean : downLoadBeen) {
                    download(downLoadBeen.get(0).getUrl());
                }
                return null;
            }
        });
    }
    public void start(DownLoadBean bean){
        try {
            download(bean.getUrl());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startAll() {
        isStopAll = false;
        DBManager.getSingleton(context).searchDownloadByStatus(DownLoadStatus.PAUSED).flatMap(new Function<List<DownLoadBean>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull List<DownLoadBean> downLoadBeen) throws Exception {
                if (downLoadBeen.size() > 0) {
                    return null;
                }
                for (DownLoadBean bean : downLoadBeen) {
                    download(downLoadBeen.get(0).getUrl());
                }
                startOtherWaiting();
                return null;
            }
        });
    }
    public void pause(DownLoadBean bean){
        Disposable disposable = disposableMap.get(bean.getUrl());
        if(disposable != null){
            disposable.dispose();
        }
    }

    public void pauseAll() {
        isStopAll = true;
        Iterator iterator = disposableMap.keySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Disposable val = (Disposable) entry.getValue();
            val.dispose();
        }
    }
    public void delete(DownLoadBean bean){
        pause(bean);
        downloadHelper.delete(bean);
    }
    public void deleteAll(){
        pauseAll();
        downloadHelper.deleteAll();
    }

    public ObservableSource<DownLoadStatus> getDownStatus(String url){
       return DBManager.getSingleton(context).searchDownloadByUrl(url)
                .flatMap(new Function<DownLoadBean, ObservableSource<DownLoadStatus>>() {
                    @Override
                    public ObservableSource<DownLoadStatus> apply(@NonNull DownLoadBean bean) throws Exception {
                        return Observable.just(bean.getStatus());
                    }
                });
    }

    public ObservableSource<List<DownLoadBean>> getDownLoading(){
        return DBManager.getSingleton(context).searchDownloadByAll()
                .flatMap(new Function<List<DownLoadBean>, ObservableSource<List<DownLoadBean>>>() {
                    @Override
                    public ObservableSource<List<DownLoadBean>> apply(@NonNull List<DownLoadBean> downLoadBeen) throws Exception {
                        List<DownLoadBean> downLoadBeanList = new ArrayList<DownLoadBean>();
                        for (DownLoadBean bean:downLoadBeen){
                            if(bean.getStatus().getStatus() != DownLoadStatus.COMPLETED){
                                downLoadBeanList.add(bean);
                            }
                        }
                        return Observable.just(downLoadBeanList);
                    }
                });
    }

    public ObservableSource<List<DownLoadBean>> getDownLoadCompleted(){
        return DBManager.getSingleton(context).searchDownloadByStatus(DownLoadStatus.COMPLETED)
                .flatMap(new Function<List<DownLoadBean>, ObservableSource<List<DownLoadBean>>>() {
                    @Override
                    public ObservableSource<List<DownLoadBean>> apply(@NonNull List<DownLoadBean> downLoadBeen) throws Exception {
                        return Observable.just(downLoadBeen);
                    }
                });
    }
}
