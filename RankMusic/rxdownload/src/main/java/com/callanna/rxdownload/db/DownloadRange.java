package com.callanna.rxdownload.db;

/**
 * Created by Callanna on 2017/7/16.
 */

public class DownloadRange {
    public long start;
    public long end;
    public long size;

    public DownloadRange(long start, long end) {
        this.start = start;
        this.end = end;
        this.size = end - start + 1;
    }

    public boolean legal() {
        return start <= end;
    }
}
