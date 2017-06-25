package com.callanna.rankmusic.ui.fragment.play

import android.view.View.GONE
import android.view.View.VISIBLE
import com.callanna.rankmusic.databinding.FragmentLrcBinding
import com.callanna.rankmusic.util.runUI
import kotlinx.android.synthetic.main.fragment_lrc.*

/**
 * Created by Callanna on 2017/6/8.
 */
class LrcFragment : com.callanna.rankmusic.ui.activity.base.BaseBingingFragment<FragmentLrcBinding>() {
    private lateinit var playPresenter: com.callanna.rankmusic.mvp.presenter.PlayPresenter
    private var lrcSong = ""
    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): com.callanna.rankmusic.databinding.FragmentLrcBinding {
        return com.callanna.rankmusic.databinding.FragmentLrcBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        if (!lrcSong.equals("") && lrcview != null) {
            lrcview.loadLrc(lrcSong)
        }
        with(mBinding!!){
            lrcview!!.setOnClickListener {
                lrcview.visibility = GONE
                imvLrcSong.visibility = VISIBLE
            }
            imvLrcSong!!.setOnClickListener {
                lrcview.visibility = VISIBLE
                imvLrcSong.visibility = GONE
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if(com.callanna.rankmusic.App.Companion.instance.currentSong != null)
            com.bumptech.glide.Glide.with(context).load(com.callanna.rankmusic.App.Companion.instance.currentSong?.url).into( imvLrcSong)
    }

    fun setSongLrc(lrc: String) {
        lrcSong = lrc
        if(context == null){
            return
        }
        context.runUI {
          lrcview.loadLrc(lrc)
        }
        if(com.callanna.rankmusic.App.Companion.instance.currentSong != null)
            com.bumptech.glide.Glide.with(context).load(com.callanna.rankmusic.App.Companion.instance.currentSong?.url).into( imvLrcSong)
    }

    fun setPresenter(playpresenter: com.callanna.rankmusic.mvp.presenter.PlayPresenter) {
        playPresenter = playpresenter
    }

    fun update(time: Long) {
        if(com.callanna.rankmusic.App.Companion.instance.currentSong == null ||context== null){
            return
        }
         com.bumptech.glide.Glide.with(context).load(com.callanna.rankmusic.App.Companion.instance.currentSong?.albumpic_big).into( imvLrcSong)
        if( lrcview != null && ! lrcview.hasLrc()){
           lrcview.loadLrc(lrcSong)
        }
        if ( lrcview != null &&  lrcview.visibility != GONE)
            context.runUI {  lrcview!!.updateTime(time * 1000) }
    }

    companion object {
        fun newInstance(): com.callanna.rankmusic.ui.fragment.play.LrcFragment {
            val fragment = com.callanna.rankmusic.ui.fragment.play.LrcFragment()
            val bundle = android.os.Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}