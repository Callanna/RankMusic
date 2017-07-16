package com.callanna.rxdownload.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Callanna on 2017/7/16.
 */

public class DownLoadStatus implements Parcelable {
    public static final Parcelable.Creator<DownLoadStatus> CREATOR
            = new Parcelable.Creator<DownLoadStatus>() {
        @Override
        public DownLoadStatus createFromParcel(Parcel source) {
            return new DownLoadStatus(source);
        }

        @Override
        public DownLoadStatus[] newArray(int size) {
            return new DownLoadStatus[size];
        }
    };
    public static final int NORMAL = 0x01;      //未下载
    public static final int WAITING = 0x02;     //等待中
    public static final int STARTED = 0x03;     //已开始下载
    public static final int PAUSED = 0x04;      //已暂停
    public static final int CANCELED = 0x05;    //已取消
    public static final int COMPLETED = 0x06;   //已完成
    public static final int FAILED = 0x07;      //下载失败

    private int status;

    private long downloadsize;

    private long totalsize;



    public DownLoadStatus() {
    }

    public DownLoadStatus(int flag) {
        status = flag;
    }

    public DownLoadStatus(long downloadSize, long totalSize) {
        this.downloadsize = downloadSize;
        this.totalsize = totalSize;
    }

    public DownLoadStatus(int flag, long downloadSize, long totalSize) {
        this.status = flag;
        this.downloadsize = downloadSize;
        this.totalsize = totalSize;
    }

    protected DownLoadStatus(Parcel in) {
        this.status = in.readInt();
        this.totalsize = in.readLong();
        this.downloadsize = in.readLong();
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public long getTotalSize() {
        return totalsize;
    }

    public void setTotalSize(long totalSize) {
        this.totalsize = totalSize;
    }

    public long getDownloadSize() {
        return downloadsize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadsize = downloadSize;
    }

    /**
     * 获得格式化的总Size
     *
     * @return example: 2KB , 10MB
     */
    public String getFormatTotalSize() {
        return formatSize(totalsize);
    }

    public String getFormatDownloadSize() {
        return formatSize(downloadsize);
    }

    /**
     * 获得格式化的状态字符串
     *
     * @return example: 2MB/36MB
     */
    public String getFormatStatusString() {
        return getFormatDownloadSize() + "/" + getFormatTotalSize();
    }

    /**
     * 获得下载的百分比, 保留两位小数
     *
     * @return example: 5.25%
     */
    public String getPercent() {
        String percent;
        Double result;
        if (totalsize == 0L) {
            result = 0.0;
        } else {
            result = downloadsize * 1.0 / totalsize;
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
        percent = nf.format(result);
        return percent;
    }

    /**
     * 获得下载的百分比数值
     *
     * @return example: 5%  will return 5, 10% will return 10.
     */
    public long getPercentNumber() {
        double result;
        if (totalsize == 0L) {
            result = 0.0;
        } else {
            result = downloadsize * 1.0 / totalsize;
        }
        return (long) (result * 100);
    }
    /**
     * Format file size to String
     *
     * @param size long
     * @return String
     */
    public  String formatSize(long size) {
        String hrSize;
        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);
        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" B");
        }
        return hrSize;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeLong(this.totalsize);
        dest.writeLong(this.downloadsize);
    }
}
