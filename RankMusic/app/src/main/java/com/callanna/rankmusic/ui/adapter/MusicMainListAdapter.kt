package com.callanna.rankmusic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.ItemMusicBinding

/**
 * Created by Callanna on 2017/6/4.
 */
class MusicMainListAdapter(private val mList:List<Music>, type:Int = 0):BaseBindingAdapter<ItemMusicBinding>(){
    override fun getItemCount(): Int {
      return  mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataBoundViewHolder<ItemMusicBinding> {
       return DataBoundViewHolder(ItemMusicBinding.inflate(LayoutInflater.from(parent!!.context),parent,false))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemMusicBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.song = mList[position]
        holder.binding.tvRank.text = ""+(position +1)
        holder.binding.executePendingBindings()
    }
  companion object{
      val TYPE_MAIN = 0
      val TYPE_LIST = 1
  }
}