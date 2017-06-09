package com.callanna.rankmusic.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
    }

    fun setSongLrc(lrc: String) {
        Log.d("duanyl", "songrlc 1 :" + lrc)
        lrcSong = lrc
        context.runUI {
            lrcview.loadLrc(lrc)
        }
    }

    fun setPresenter(playpresenter: PlayPresenter) {
        playPresenter = playpresenter
    }

    fun update(time: Long) {
        if(lrcview != null && !lrcview.hasLrc()){
          lrcview.loadLrc(lrcSong)
        }
        if (lrcview != null)
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