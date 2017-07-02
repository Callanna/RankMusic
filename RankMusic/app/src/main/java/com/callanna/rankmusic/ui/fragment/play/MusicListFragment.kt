package com.callanna.rankmusic.ui.fragment.play

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.callanna.rankmusic.App
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.FragmentMusiclistBinding
import com.callanna.rankmusic.mvp.presenter.PlayPresenter
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.ui.adapter.PlayMusicListAdapter
import kotlinx.android.synthetic.main.fragment_musiclist.*
import java.util.*

/**
 * Created by Callanna on 2017/6/6.
 */

class MusicListFragment : BaseBingingFragment<FragmentMusiclistBinding>() {
    private var mList = ArrayList<Music>()
    private var mAdapter = PlayMusicListAdapter(mList)
    private lateinit var playPresenter : PlayPresenter
    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMusiclistBinding {
        return FragmentMusiclistBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        with(mBinding!!){
            recyclerviewMusicList.adapter = mAdapter
            recyclerviewMusicList.layoutManager = LinearLayoutManager(context)
            mAdapter.notifyDataSetChanged()
            mAdapter.setOnItemClickListener {
                pos ->
                playPresenter.play(pos,"")
                mAdapter.notifyDataSetChanged()
            }
        }
    }
    fun setPresenter(playpresenter : PlayPresenter){
        playPresenter = playpresenter
    }

    fun setData(results: List<Music>) {
        mList.clear()
        mList.addAll(results)
        mAdapter.notifyDataSetChanged()
    }

    fun update(){
        mAdapter.notifyDataSetChanged()
        if( recyclerviewMusicList != null) {
           recyclerviewMusicList.smoothScrollToPosition(App.instance.currentPosition + 1)
        }
    }

    companion object {
        fun newInstance(): MusicListFragment {
            val fragment = MusicListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
