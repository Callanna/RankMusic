package com.callanna.rankmusic.ui.fragment

import com.callanna.rankmusic.databinding.FragmentDownloadBinding
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import kotlinx.android.synthetic.main.fragment_download.*

/**
 * Created by Callanna on 2017/6/25.
 */
class DownLoadingFragment : BaseBingingFragment<FragmentDownloadBinding>() {


    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): FragmentDownloadBinding {

        return FragmentDownloadBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        recycleview_list
    }
    companion object {
        fun newInstance(): DownLoadingFragment {
            val fragment = DownLoadingFragment()
            val bundle = android.os.Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}