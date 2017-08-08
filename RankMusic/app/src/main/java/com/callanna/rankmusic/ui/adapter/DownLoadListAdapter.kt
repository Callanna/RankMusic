package com.callanna.rankmusic.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.callanna.rankmusic.App
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.ItemDownloadBinding
import com.callanna.rankmusic.db.MusicDBManager
import com.callanna.rankmusic.util.Constants
import zlc.season.rxdownload2.RxDownload
import zlc.season.rxdownload2.entity.DownloadFlag







/**
 * Created by Callanna on 2017/6/4.
 */
class DownLoadListAdapter(private val mList:List<Music>):BaseBindingAdapter<ItemDownloadBinding>(){
    override fun getItemCount(): Int {
      return  mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataBoundViewHolder<ItemDownloadBinding> {
       return DataBoundViewHolder(ItemDownloadBinding.inflate(LayoutInflater.from(parent!!.context),parent,false))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemDownloadBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.song = mList[position]
        holder.binding.imvCancle.setOnClickListener {
            MusicDBManager.instance.saveDownLoad( holder.binding.song.songid.toString(),"")
            RxDownload.getInstance(App.instance).deleteServiceDownload(holder.binding.song.downUrl,true)
            statusListener?.statusChanged()
        }
        holder.binding.tvStop.setText(App.instance.getString(R.string.stop))
        RxDownload.getInstance(App.instance).receiveDownloadStatus(holder.binding.song.downUrl)
                .subscribe { downloadEvent ->
                  Log.d("duanyl","downloadEvent.flag:"+downloadEvent.flag)
                    if (downloadEvent.getFlag() === DownloadFlag.FAILED || downloadEvent.flag === DownloadFlag.PAUSED) {
                        val throwable = downloadEvent.getError()
                        holder.binding.tvStop.setText(App.instance.getString(R.string.start))
                    }else if(downloadEvent.getFlag() === DownloadFlag.CANCELED ||downloadEvent.getFlag() === DownloadFlag.DELETED || downloadEvent.getFlag() === DownloadFlag.COMPLETED) {
                        statusListener?.statusChanged()
                        MusicDBManager.instance.saveDownLoad( holder.binding.song.songid.toString(),"")
                    }else  if(downloadEvent.getFlag() === DownloadFlag.COMPLETED){
                        holder.binding.tvStop.visibility = View.GONE
                        holder.binding.progressbar.visibility = View.GONE
                        holder.binding.tvProgress.visibility = View.GONE
                        holder.binding.imvCancle.setImageResource(R.mipmap.ic_ok)
                        MusicDBManager.instance.saveDownLoad( holder.binding.song.songid.toString(),Constants.DownLoad)
                    }else{
                            val status = downloadEvent.getDownloadStatus()
                            holder.binding.progressbar.setProgress(status.getDownloadSize() as Int)
                            holder.binding.tvProgress.setText(status.getPercent())
                        }
                    }

        holder.binding.tvStop.setOnClickListener {
            if(holder.binding.tvStop.text.equals( App.instance.getString(R.string.stop))){
                holder.binding.tvStop.setText(App.instance.getString(R.string.start))
                RxDownload.getInstance(App.instance).pauseServiceDownload(holder.binding.song.downUrl)
            }else{
                holder.binding.tvStop.setText(App.instance.getString(R.string.stop))
                RxDownload.getInstance(App.instance).serviceDownload(holder.binding.song.downUrl)
            }
        }
        holder.binding.executePendingBindings()
    }
   fun setDownLoadStatusListener( listener:DownLoadStatusListener) {
       statusListener = listener
   }
    private  var statusListener:DownLoadStatusListener ?= null

    interface DownLoadStatusListener{
        fun statusChanged()
    }
}