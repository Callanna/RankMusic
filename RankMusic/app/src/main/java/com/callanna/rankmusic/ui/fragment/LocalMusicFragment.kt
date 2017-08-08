package com.callanna.rankmusic.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.FragmentLocalmusicBinding
import com.callanna.rankmusic.ui.activity.PlayActivity
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.ui.adapter.LocalMusicListAdapter
import java.util.*

/**
 * Created by Callanna on 2017/6/25.
 */
class LocalMusicFragment : BaseBingingFragment<FragmentLocalmusicBinding>() {
    private var mList = ArrayList<Music>()
    private var mAdapter = LocalMusicListAdapter(mList)
    private var type:String=""

    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): FragmentLocalmusicBinding {
        return FragmentLocalmusicBinding.inflate(inflater, container, false)
    }

    override fun initView() {
       type = arguments.getString("type")
       with(mBinding!!){
           recycleview.adapter = mAdapter
           recycleview.layoutManager = LinearLayoutManager(context)
           mAdapter.notifyDataSetChanged()
           mAdapter.setOnItemClickListener {
               pos ->
               PlayActivity.startActivity(context, type, pos, recycleview)
           }
       }
    }
    companion object {
        fun newInstance(type:String): LocalMusicFragment{
            val fragment = LocalMusicFragment()
            val bundle = android.os.Bundle()
            bundle.putString("type",type)
            fragment.arguments = bundle
            return fragment
        }
    }

    fun setList(result: List<Music>) {
        mList.clear()
        mList.addAll(result)
        mAdapter.notifyDataSetChanged()
    }
}