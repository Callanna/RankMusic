package com.callanna.rankmusic.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.FragmentDownloadBinding
import com.callanna.rankmusic.db.MusicDBManager
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.ui.adapter.DownLoadListAdapter
import com.callanna.rankmusic.util.Constants
import java.util.*

/**
 * Created by Callanna on 2017/6/25.
 */
class DownLoadingFragment : BaseBingingFragment<FragmentDownloadBinding>() {
    private var mList = ArrayList<Music>()
    private var mAdapter = DownLoadListAdapter(mList)

    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): FragmentDownloadBinding {
        return FragmentDownloadBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        with(mBinding!!) {
            mAdapter.setDownLoadStatusListener(object:DownLoadListAdapter.DownLoadStatusListener{
                override fun statusChanged() {
                    mList.clear()
                    mList.addAll(MusicDBManager.instance.getDownLoad(Constants.DownLoadING))
                    mAdapter.notifyDataSetChanged()
                }
            })
            recycleview.adapter = mAdapter
            recycleview.layoutManager = LinearLayoutManager(context)
            mAdapter.notifyDataSetChanged()
        }
    }
    companion object {
        fun newInstance(): DownLoadingFragment {
            val fragment = DownLoadingFragment()
            val bundle = android.os.Bundle()
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