package com.callanna.rankmusic.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.databinding.ActivityPlayBinding
import com.callanna.rankmusic.mvp.contract.PlayContract
import com.callanna.rankmusic.ui.activity.base.BaseBindingActivity

class PlayActivity : BaseBindingActivity<ActivityPlayBinding>(),PlayContract.View {


    override fun initView() {
        
    }

    override fun createDataBinding(savedInstanceState: Bundle?):ActivityPlayBinding {
        return DataBindingUtil.setContentView(this,R.layout.activity_play)
    }

    override fun setSongList(result: List<Music>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun currentPlayTime(now: Int, total: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMode(mode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
