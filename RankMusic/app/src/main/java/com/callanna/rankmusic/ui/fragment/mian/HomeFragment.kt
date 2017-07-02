package com.callanna.rankmusic.ui.fragment.mian

import android.support.v7.widget.LinearLayoutManager
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.dagger.compontent.MainMusicModule
import com.callanna.rankmusic.databinding.FragmentHomeBinding
import com.callanna.rankmusic.mvp.contract.MainContract
import com.callanna.rankmusic.mvp.presenter.MainPresenter
import com.callanna.rankmusic.ui.activity.PlayActivity
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.ui.adapter.MusicMainListAdapter
import com.callanna.rankmusic.util.Constants
import com.callanna.rankmusic.util.getMainComponent
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseBingingFragment<FragmentHomeBinding>(), MainContract.View {

    private var mListHot: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListRock: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListLocal: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListUK: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListKorea: java.util.ArrayList<Music> = java.util.ArrayList()
    private lateinit var mAdapterHot:  MusicMainListAdapter
    private lateinit var mAdapterRock:  MusicMainListAdapter
    private lateinit var mAdapterLocal:  MusicMainListAdapter
    private lateinit var mAdapterUK:  MusicMainListAdapter
    private lateinit var mAdapterKorea:  MusicMainListAdapter



    @javax.inject.Inject lateinit var mPresenter : MainPresenter

    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): com.callanna.rankmusic.databinding.FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        context.getMainComponent().plus(MainMusicModule(this)).inject(this)

        mAdapterHot =  MusicMainListAdapter(mListHot)
        mAdapterKorea =  MusicMainListAdapter(mListKorea)
        mAdapterLocal =  MusicMainListAdapter(mListLocal)
        mAdapterRock =  MusicMainListAdapter(mListRock)
        mAdapterUK =  MusicMainListAdapter(mListUK)

        with(mBinding!!){
            listHot.adapter = mAdapterHot
            listHot.layoutManager =  LinearLayoutManager(context)
            mAdapterHot.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context, Constants.HOT_SONG, pos, listHot)
            }
            listKorea.adapter = mAdapterKorea
            listKorea.layoutManager = LinearLayoutManager(context)
            mAdapterKorea.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context, Constants.KOREA, pos, listKorea)
            }
            listLocal.adapter = mAdapterLocal
            listLocal.layoutManager =  LinearLayoutManager(context)
            mAdapterLocal.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context, Constants.LOCAL, pos, listLocal)
            }
            listRock.adapter = mAdapterRock
            listRock.layoutManager =  LinearLayoutManager(context)
            mAdapterRock.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context, Constants.ROCK, pos, listRock)
            }
            listUk.adapter = mAdapterUK
            listUk.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            mAdapterUK.setOnItemClickListener { pos ->
                PlayActivity.startActivity(context, Constants.UK, pos, list_uk)
            }
            layoutHot.setOnClickListener {
                PlayActivity.startActivity(context, Constants.HOT_SONG, 0)
            }
            layoutUk.setOnClickListener {
                PlayActivity.startActivity(context, Constants.UK, 0)
            }
            layoutLocal.setOnClickListener {
                PlayActivity.startActivity(context, Constants.LOCAL, 0)
            }
            layoutKorea.setOnClickListener {
                PlayActivity.startActivity(context, Constants.KOREA, 0)
            }
            layoutRock.setOnClickListener {
                PlayActivity.startActivity(context, Constants.ROCK, 0)
            }
            imvSearch.setOnClickListener {
                PlayActivity.searchByKey(context, et_search.text.toString())
            }
        }

        mPresenter.getData()


    }

    override fun onResume() {
        super.onResume()
       loading_hot.visibility = android.view.View.INVISIBLE
       loading_uk.visibility = android.view.View.INVISIBLE
       loading_local.visibility = android.view.View.INVISIBLE
       loading_rock.visibility = android.view.View.INVISIBLE
       loading_korea.visibility = android.view.View.INVISIBLE
        when(com.callanna.rankmusic.App.Companion.playCurrentType){
            Constants.HOT_SONG ->
               loading_hot.visibility = android.view.View.VISIBLE
            Constants.UK ->
               loading_uk.visibility = android.view.View.VISIBLE
            Constants.LOCAL ->
               loading_local.visibility = android.view.View.VISIBLE
            Constants.ROCK ->
               loading_rock.visibility = android.view.View.VISIBLE
            Constants.KOREA ->
               loading_korea.visibility = android.view.View.VISIBLE
        }
    }
    override fun setHotSong(result: List<com.callanna.rankmusic.bean.Music>) {
        android.util.Log.d("duanyl","setHotSong")
        mListHot.addAll(result.subList(0,3))
        mAdapterHot.notifyDataSetChanged()
    }

    override fun setRock(result: List<com.callanna.rankmusic.bean.Music>) {
        android.util.Log.d("duanyl","setRock")
        mListRock.addAll(result.subList(0,3))
        mAdapterRock.notifyDataSetChanged()
    }

    override fun setLocal(result: List<com.callanna.rankmusic.bean.Music>) {
        android.util.Log.d("duanyl","setLocal")
        mListLocal.addAll(result.subList(0,3))
        mAdapterLocal.notifyDataSetChanged()
    }

    override fun setUK(result: List<com.callanna.rankmusic.bean.Music>) {
        android.util.Log.d("duanyl","setUK")
        mListUK.addAll(result.subList(0,3))
        mAdapterUK.notifyDataSetChanged()
    }

    override fun setKorea(result: List<com.callanna.rankmusic.bean.Music>) {
        android.util.Log.d("duanyl","setKorea")
        mListKorea.addAll(result.subList(0,3))
        mAdapterKorea.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscribe()
        mBinding.unbind()
    }


    companion object {
        fun newInstance():  HomeFragment {
            val fragment =  HomeFragment()
            val bundle = android.os.Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
