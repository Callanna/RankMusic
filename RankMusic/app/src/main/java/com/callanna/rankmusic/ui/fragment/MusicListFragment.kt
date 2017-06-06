package com.callanna.rankmusic.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.FragmentMusiclistBinding
import com.callanna.rankmusic.mvp.presenter.PlayPresenter
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.ui.adapter.PlayMusicListAdapter
import java.util.*

/**
 * Created by Callanna on 2017/6/6.
 */

class MusicListFragment : BaseBingingFragment<FragmentMusiclistBinding>() {
    private var mList = ArrayList<Music>()
    private lateinit var mAdapter: PlayMusicListAdapter
    private lateinit var playPresenter :PlayPresenter
    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMusiclistBinding {
        return FragmentMusiclistBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        mAdapter = PlayMusicListAdapter(mList)
        with(mBinding!!){
            recyclerviewMusic.adapter = mAdapter
            recyclerviewMusic.layoutManager = LinearLayoutManager(context)
            mAdapter.setOnItemClickListener {
                pos ->
                playPresenter.play(pos,"")
                mAdapter.notifyDataSetChanged()
            }
        }
    }
    fun setPresenter(playpresenter :PlayPresenter){
        playPresenter = playpresenter
    }

    fun setData(results: List<Music>) {
        mList.addAll(results)
        mAdapter.notifyDataSetChanged()
    }

    fun update(){
        mAdapter.notifyDataSetChanged()
    }
    companion object {
        val ANDROID = "ANDROID"
        fun newInstance(): MusicListFragment {
            val fragment = MusicListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
