package com.callanna.rankmusic.ui.activity

import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.dagger.compontent.MainMusicModule
import com.callanna.rankmusic.databinding.ActivityMainBinding
import com.callanna.rankmusic.mvp.contract.MainContract
import com.callanna.rankmusic.mvp.presenter.MainPresenter
import com.callanna.rankmusic.ui.activity.base.BaseBindingActivity
import com.callanna.rankmusic.ui.adapter.MusicListAdapter
import com.callanna.rankmusic.util.getMainComponent
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseBindingActivity<ActivityMainBinding>(),MainContract.View {


    private var mListHot:ArrayList<Music> = ArrayList()
    private val mListRock:ArrayList<Music> = ArrayList()
    private val mListLocal:ArrayList<Music> = ArrayList()
    private val mListUK:ArrayList<Music> = ArrayList()
    private val mListKorea:ArrayList<Music> = ArrayList()
    private lateinit var mAdapterHot: MusicListAdapter
    private lateinit var mAdapterRock: MusicListAdapter
    private lateinit var mAdapterLocal: MusicListAdapter
    private lateinit var mAdapterUK: MusicListAdapter
    private lateinit var mAdapterKorea: MusicListAdapter

    @Inject lateinit var mPresenter : MainPresenter

    private var player: MediaPlayer = MediaPlayer()

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityMainBinding{

        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun initView() {
        getMainComponent().plus(MainMusicModule(this)).inject(this)

        mAdapterHot = MusicListAdapter(mListHot)
        mAdapterKorea = MusicListAdapter(mListKorea)
        mAdapterLocal = MusicListAdapter(mListLocal)
        mAdapterRock = MusicListAdapter(mListRock)
        mAdapterUK = MusicListAdapter(mListUK)

        with(mBinding!!){
            listHot.adapter = mAdapterHot
            listHot.layoutManager =LinearLayoutManager(context)
            mAdapterHot.setOnItemClickListener { pos ->
                //TODO

            }
            listKorea.adapter = mAdapterKorea
            listKorea.layoutManager =LinearLayoutManager(context)
            mAdapterKorea.setOnItemClickListener { pos ->
                //TODO

            }
            listLocal.adapter = mAdapterLocal
            listLocal.layoutManager =LinearLayoutManager(context)
            mAdapterLocal.setOnItemClickListener { pos ->
                //TODO

            }
            listRock.adapter = mAdapterRock
            listRock.layoutManager =LinearLayoutManager(context)
            mAdapterRock.setOnItemClickListener { pos ->
                //TODO

            }
            list_uk.adapter = mAdapterUK
            list_uk.layoutManager =LinearLayoutManager(context)
            mAdapterUK.setOnItemClickListener { pos ->
                //TODO

            }
        }

        mPresenter.getData()

        player.setDataSource(this, Uri.parse("http://ws.stream.qqmusic.qq.com/108709929.m4a?fromtag=46"))
        player.setOnPreparedListener {
            listener->
            player.start()
        }
        player.prepareAsync()
    }

    override fun setHotSong(result: List<Music>) {
        Log.d("duanyl","setHotSong")
        mListHot.addAll(result.subList(0,3))
        mAdapterHot.notifyDataSetChanged()
    }

    override fun setRock(result: List<Music>) {
        Log.d("duanyl","setRock")
        mListRock.addAll(result.subList(0,3))
        mAdapterRock.notifyDataSetChanged()
    }

    override fun setLocal(result: List<Music>) {
        Log.d("duanyl","setLocal")
        mListLocal.addAll(result.subList(0,3))
        mAdapterLocal.notifyDataSetChanged()
    }

    override fun setUK(result: List<Music>) {
        Log.d("duanyl","setUK")
        mListUK.addAll(result.subList(0,3))
        mAdapterUK.notifyDataSetChanged()
    }

    override fun setKorea(result: List<Music>) {
        Log.d("duanyl","setKorea")
        mListKorea.addAll(result.subList(0,3))
        mAdapterKorea.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
