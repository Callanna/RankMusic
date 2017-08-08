package com.callanna.rankmusic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.callanna.rankmusic.App
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.ItemLocalmusicBinding
import com.callanna.rankmusic.util.MediaPlayerUtil

/**
 * Created by Callanna on 2017/6/4.
 */
class LocalMusicListAdapter(private val mList:List<Music>):BaseBindingAdapter<ItemLocalmusicBinding>(){
    override fun getItemCount(): Int {
      return  mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataBoundViewHolder<ItemLocalmusicBinding> {
       return DataBoundViewHolder(ItemLocalmusicBinding.inflate(LayoutInflater.from(parent!!.context),parent,false))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemLocalmusicBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.song = mList[position]
        if(holder.binding.song.url.equals(MediaPlayerUtil.instance.getPlayUrl())){//正在播放的歌曲
            holder.binding.tvSinger.setTextColor(App.instance.resources.getColor(R.color.write))
            holder.binding.tvSongname.setTextColor(App.instance.resources.getColor(R.color.write))
        }else{
            holder.binding.tvSinger.setTextColor(App.instance.resources.getColor(R.color.tr_write_half))
            holder.binding.tvSongname.setTextColor(App.instance.resources.getColor(R.color.tr_write_half))
        }
        holder.binding.executePendingBindings()
    }

}