package com.callanna.rankmusic.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.callanna.rankmusic.App
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.ItemMusiclistBinding
import com.callanna.rankmusic.util.MediaPlayerUtil

/**
 * Created by Callanna on 2017/6/4.
 */
class PlayMusicListAdapter(private val mList:List<Music>):BaseBindingAdapter<ItemMusiclistBinding>(){
    override fun getItemCount(): Int {
      return  mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataBoundViewHolder<ItemMusiclistBinding> {
       return DataBoundViewHolder(ItemMusiclistBinding.inflate(LayoutInflater.from(parent!!.context),parent,false))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemMusiclistBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.song = mList[position]
        holder.binding.tvRank.text = ""+(position +1)
        if(holder.binding.song.url.equals(MediaPlayerUtil.instance.getPlayUrl())){//正在播放的歌曲
            holder.binding.tvRank.setTextColor(App.instance.resources.getColor(R.color.write))
            holder.binding.tvSinger.setTextColor(App.instance.resources.getColor(R.color.write))
            holder.binding.tvSongname.setTextColor(App.instance.resources.getColor(R.color.write))
            holder.binding.loading.visibility = View.VISIBLE
        }else{
            holder.binding.loading.visibility = View.GONE
            holder.binding.tvRank.setTextColor(App.instance.resources.getColor(R.color.tr_write_half))
            holder.binding.tvSinger.setTextColor(App.instance.resources.getColor(R.color.tr_write_half))
            holder.binding.tvSongname.setTextColor(App.instance.resources.getColor(R.color.tr_write_half))
        }
        holder.binding.executePendingBindings()
    }

}