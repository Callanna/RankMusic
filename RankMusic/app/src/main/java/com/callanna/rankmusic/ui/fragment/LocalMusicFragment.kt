package com.callanna.rankmusic.ui.fragment

import com.callanna.rankmusic.databinding.FragmentDownloadBinding
import com.callanna.rankmusic.databinding.FragmentLocalmusicBinding
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment

/**
 * Created by Callanna on 2017/6/25.
 */
class LocalMusicFragment : BaseBingingFragment<FragmentLocalmusicBinding>() {


    override fun createDataBinding(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): FragmentLocalmusicBinding {
        return FragmentLocalmusicBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }
    companion object {
        fun newInstance(): LocalMusicFragment{
            val fragment = LocalMusicFragment()
            val bundle = android.os.Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}