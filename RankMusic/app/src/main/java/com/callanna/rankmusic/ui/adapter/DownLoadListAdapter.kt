package com.callanna.rankmusic.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.callanna.rankmusic.App
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.ItemDownloadBinding
import com.callanna.rankmusic.databinding.ItemMusiclistBinding
import com.callanna.rankmusic.util.MediaPlayerUtil

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

    }

}