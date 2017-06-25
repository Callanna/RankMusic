package com.callanna.rankmusic.ui.fragment.mian

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.callanna.rankmusic.R
import com.callanna.rankmusic.databinding.FragmentHomeBinding
import com.callanna.rankmusic.databinding.FragmentMeBinding
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment

class MeFragment : BaseBingingFragment<FragmentMeBinding>(){
    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMeBinding {
        return FragmentMeBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }
    companion object {
        fun newInstance(): MeFragment {
            val fragment = MeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
