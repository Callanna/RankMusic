package com.callanna.rankmusic.ui.fragment

import com.callanna.rankmusic.databinding.FragmentDownloadBinding
import com.callanna.rankmusic.databinding.FragmentLrcBinding
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment

/**
 * Created by Callanna on 2017/6/25.
 */
class DowLoadingFragment : BaseBingingFragment<FragmentDownloadBinding>() {


    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): FragmentDownloadBinding {
        return FragmentDownloadBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }
    companion object {
        fun newInstance(): DowLoadingFragment{
            val fragment = DowLoadingFragment()
            val bundle = android.os.Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}