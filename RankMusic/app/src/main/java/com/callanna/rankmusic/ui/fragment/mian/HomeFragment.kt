package com.callanna.rankmusic.ui.fragment.mian

import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.FragmentHomeBinding
import com.callanna.rankmusic.util.Constants
import com.callanna.rankmusic.util.getMainComponent
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : com.callanna.rankmusic.ui.activity.base.BaseBingingFragment<FragmentHomeBinding>(), com.callanna.rankmusic.mvp.contract.MainContract.View {

    private var mListHot: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListRock: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListLocal: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListUK: java.util.ArrayList<Music> = java.util.ArrayList()
    private val mListKorea: java.util.ArrayList<Music> = java.util.ArrayList()
    private lateinit var mAdapterHot: com.callanna.rankmusic.ui.adapter.MusicMainListAdapter
    private lateinit var mAdapterRock: com.callanna.rankmusic.ui.adapter.MusicMainListAdapter
    private lateinit var mAdapterLocal: com.callanna.rankmusic.ui.adapter.MusicMainListAdapter
    private lateinit var mAdapterUK: com.callanna.rankmusic.ui.adapter.MusicMainListAdapter
    private lateinit var mAdapterKorea: com.callanna.rankmusic.ui.adapter.MusicMainListAdapter



    @javax.inject.Inject lateinit var mPresenter : com.callanna.rankmusic.mvp.presenter.MainPresenter

    private var player: android.media.MediaPlayer = android.media.MediaPlayer()

    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): com.callanna.rankmusic.databinding.FragmentHomeBinding {
        return com.callanna.rankmusic.databinding.FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        context.getMainComponent().plus(com.callanna.rankmusic.dagger.compontent.MainMusicModule(this)).inject(this)

        mAdapterHot = com.callanna.rankmusic.ui.adapter.MusicMainListAdapter(mListHot)
        mAdapterKorea = com.callanna.rankmusic.ui.adapter.MusicMainListAdapter(mListKorea)
        mAdapterLocal = com.callanna.rankmusic.ui.adapter.MusicMainListAdapter(mListLocal)
        mAdapterRock = com.callanna.rankmusic.ui.adapter.MusicMainListAdapter(mListRock)
        mAdapterUK = com.callanna.rankmusic.ui.adapter.MusicMainListAdapter(mListUK)

        with(mBinding!!){
            listHot.adapter = mAdapterHot
            listHot.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            mAdapterHot.setOnItemClickListener { pos ->
                com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.HOT_SONG, pos, listHot)
            }
            listKorea.adapter = mAdapterKorea
            listKorea.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            mAdapterKorea.setOnItemClickListener { pos ->
                com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.KOREA, pos, listKorea)
            }
            listLocal.adapter = mAdapterLocal
            listLocal.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            mAdapterLocal.setOnItemClickListener { pos ->
                com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.LOCAL, pos, listLocal)
            }
            listRock.adapter = mAdapterRock
            listRock.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            mAdapterRock.setOnItemClickListener { pos ->
                com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.ROCK, pos, listRock)
            }
            kotlinx.android.synthetic.main.fragment_home.list_uk.adapter = mAdapterUK
            kotlinx.android.synthetic.main.fragment_home.list_uk.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            mAdapterUK.setOnItemClickListener { pos ->
                com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.UK, pos, list_uk)
            }
        }

        mPresenter.getData()
        kotlinx.android.synthetic.main.fragment_home.layout_hot.setOnClickListener {
            com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.HOT_SONG, 0)
        }
        kotlinx.android.synthetic.main.fragment_home.layout_uk.setOnClickListener {
            com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.UK, 0)
        }
        kotlinx.android.synthetic.main.fragment_home.layout_local.setOnClickListener {
            com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.LOCAL, 0)
        }
        kotlinx.android.synthetic.main.fragment_home.layout_korea.setOnClickListener {
            com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.KOREA, 0)
        }
        kotlinx.android.synthetic.main.fragment_home.layout_rock.setOnClickListener {
            com.callanna.rankmusic.ui.activity.PlayActivity.Companion.startActivity(context, Constants.ROCK, 0)
        }
        kotlinx.android.synthetic.main.fragment_home.imv_search.setOnClickListener {
            com.callanna.rankmusic.ui.activity.PlayActivity.Companion.searchByKey(context, et_search.text.toString())
        }

    }

    override fun onResume() {
        super.onResume()
        kotlinx.android.synthetic.main.fragment_home.loading_hot.visibility = android.view.View.INVISIBLE
        kotlinx.android.synthetic.main.fragment_home.loading_uk.visibility = android.view.View.INVISIBLE
        kotlinx.android.synthetic.main.fragment_home.loading_local.visibility = android.view.View.INVISIBLE
        kotlinx.android.synthetic.main.fragment_home.loading_rock.visibility = android.view.View.INVISIBLE
        kotlinx.android.synthetic.main.fragment_home.loading_korea.visibility = android.view.View.INVISIBLE
        when(com.callanna.rankmusic.App.Companion.playCurrentType){
            com.callanna.rankmusic.util.Constants.Companion.HOT_SONG ->
                kotlinx.android.synthetic.main.fragment_home.loading_hot.visibility = android.view.View.VISIBLE
            com.callanna.rankmusic.util.Constants.Companion.UK ->
                kotlinx.android.synthetic.main.fragment_home.loading_uk.visibility = android.view.View.VISIBLE
            com.callanna.rankmusic.util.Constants.Companion.LOCAL ->
                kotlinx.android.synthetic.main.fragment_home.loading_local.visibility = android.view.View.VISIBLE
            com.callanna.rankmusic.util.Constants.Companion.ROCK ->
                kotlinx.android.synthetic.main.fragment_home.loading_rock.visibility = android.view.View.VISIBLE
            com.callanna.rankmusic.util.Constants.Companion.KOREA ->
                kotlinx.android.synthetic.main.fragment_home.loading_korea.visibility = android.view.View.VISIBLE
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
        fun newInstance(): com.callanna.rankmusic.ui.fragment.mian.HomeFragment {
            val fragment = com.callanna.rankmusic.ui.fragment.mian.HomeFragment()
            val bundle = android.os.Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
