package com.callanna.rankmusic.ui.activity

import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.callanna.rankmusic.App
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.dagger.compontent.MainMusicModule
import com.callanna.rankmusic.databinding.ActivityMainBinding
import com.callanna.rankmusic.mvp.contract.MainContract
import com.callanna.rankmusic.mvp.presenter.MainPresenter
import com.callanna.rankmusic.ui.activity.base.BaseBindingActivity
import com.callanna.rankmusic.ui.adapter.MusicMainListAdapter
import com.callanna.rankmusic.util.Constants
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
    private lateinit var mAdapterHot: MusicMainListAdapter
    private lateinit var mAdapterRock: MusicMainListAdapter
    private lateinit var mAdapterLocal: MusicMainListAdapter
    private lateinit var mAdapterUK: MusicMainListAdapter
    private lateinit var mAdapterKorea: MusicMainListAdapter

    @Inject lateinit var mPresenter : MainPresenter

    private var player: MediaPlayer = MediaPlayer()

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityMainBinding{

        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun initView() {
        getMainComponent().plus(MainMusicModule(this)).inject(this)
        mAdapterHot = MusicMainListAdapter(mListHot)
        mAdapterKorea = MusicMainListAdapter(mListKorea)
        mAdapterLocal = MusicMainListAdapter(mListLocal)
        mAdapterRock = MusicMainListAdapter(mListRock)
        mAdapterUK = MusicMainListAdapter(mListUK)

        with(mBinding!!){
            listHot.adapter = mAdapterHot
            listHot.layoutManager =LinearLayoutManager(context)
            mAdapterHot.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context,Constants.HOT_SONG,pos)
            }
            listKorea.adapter = mAdapterKorea
            listKorea.layoutManager =LinearLayoutManager(context)
            mAdapterKorea.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context,Constants.KOREA,pos)
            }
            listLocal.adapter = mAdapterLocal
            listLocal.layoutManager =LinearLayoutManager(context)
            mAdapterLocal.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context,Constants.LOCAL,pos)
            }
            listRock.adapter = mAdapterRock
            listRock.layoutManager =LinearLayoutManager(context)
            mAdapterRock.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context,Constants.ROCK,pos)
            }
            list_uk.adapter = mAdapterUK
            list_uk.layoutManager =LinearLayoutManager(context)
            mAdapterUK.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context,Constants.UK,pos)
            }
        }

        mPresenter.getData()
        layout_hot.setOnClickListener {
            PlayActivity.startActivity(context,Constants.HOT_SONG,0)
        }
        layout_uk.setOnClickListener {
            PlayActivity.startActivity(context,Constants.UK,0)
        }
        layout_local.setOnClickListener {
            PlayActivity.startActivity(context,Constants.LOCAL,0)
        }
        layout_korea.setOnClickListener {
            PlayActivity.startActivity(context,Constants.KOREA,0)
        }
        layout_rock.setOnClickListener {
            PlayActivity.startActivity(context,Constants.ROCK,0)
        }
    }

    override fun onResume() {
        super.onResume()
        loading_hot.visibility = View.INVISIBLE
        loading_uk.visibility = View.INVISIBLE
        loading_local.visibility = View.INVISIBLE
        loading_rock.visibility = View.INVISIBLE
        loading_korea.visibility = View.INVISIBLE
        when(App.playCurrentType){
            Constants.HOT_SONG->
                loading_hot.visibility = View.VISIBLE
            Constants.UK->
                loading_uk.visibility = View.VISIBLE
            Constants.LOCAL->
                loading_local.visibility = View.VISIBLE
            Constants.ROCK->
                loading_rock.visibility = View.VISIBLE
            Constants.KOREA->
                loading_korea.visibility = View.VISIBLE
        }
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
