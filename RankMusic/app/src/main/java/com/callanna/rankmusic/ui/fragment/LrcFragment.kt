package com.callanna.rankmusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.callanna.rankmusic.App
import com.callanna.rankmusic.databinding.FragmentLrcBinding
import com.callanna.rankmusic.mvp.presenter.PlayPresenter
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.util.runUI
import kotlinx.android.synthetic.main.fragment_lrc.*

/**
 * Created by Callanna on 2017/6/8.
 */
class LrcFragment : BaseBingingFragment<FragmentLrcBinding>() {
    private lateinit var playPresenter: PlayPresenter
    private var lrcSong = ""
    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): FragmentLrcBinding {
        return FragmentLrcBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        if (!lrcSong.equals("") && lrcview!= null) {
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
        if(App.instance.currentSong != null)
            Glide.with(context).load(App.instance.currentSong?.url).into(imvLrcSong)
    }

    fun setSongLrc(lrc: String) {
        lrcSong = lrc
        if(context == null){
            return
        }
        context.runUI {
            lrcview.loadLrc(lrc)
        }
        if(App.instance.currentSong != null)
            Glide.with(context).load(App.instance.currentSong?.url).into(imvLrcSong)
    }

    fun setPresenter(playpresenter: PlayPresenter) {
        playPresenter = playpresenter
    }

    fun update(time: Long) {
        if(App.instance.currentSong == null ||context== null){
            return
        }
         Glide.with(context).load(App.instance.currentSong?.albumpic_big).into(imvLrcSong)
        if(lrcview != null && !lrcview.hasLrc()){
          lrcview.loadLrc(lrcSong)
        }
        if (lrcview != null && lrcview.visibility != GONE)
            context.runUI { lrcview!!.updateTime(time * 1000) }
    }

    companion object {
        fun newInstance(): LrcFragment {
            val fragment = LrcFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}