package com.callanna.rankmusic.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.dagger.compontent.ListMusicModule
import com.callanna.rankmusic.databinding.ActivityListBinding
import com.callanna.rankmusic.mvp.contract.ListContract
import com.callanna.rankmusic.mvp.presenter.ListPresenter
import com.callanna.rankmusic.ui.activity.base.BaseBindingActivity
import com.callanna.rankmusic.ui.fragment.DownLoadingFragment
import com.callanna.rankmusic.ui.fragment.LocalMusicFragment
import com.callanna.rankmusic.util.Constants
import com.callanna.rankmusic.util.getMainComponent
import kotlinx.android.synthetic.main.activity_play.*
import javax.inject.Inject

class ListActivity : BaseBindingActivity<ActivityListBinding>(),ListContract.View {

    @Inject lateinit var mPresenter: ListPresenter
    private var mymuisc :ArrayList<Music> = ArrayList<Music>()
    private  var mymuiscfragment = LocalMusicFragment.newInstance(Constants.MyMUSIC)
    override fun createDataBinding(savedInstanceState: Bundle?): ActivityListBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_list)
    }
    override fun initView(savedInstanceState: Bundle?) {
        if(savedInstanceState!= null){
            mBinding.type = savedInstanceState.getString(PlayActivity.TYPE)
        }else {
            mBinding.type = intent.getStringExtra(PlayActivity.TYPE)
        }
        setupToolbar(toolbar)

        getMainComponent().plus(ListMusicModule(this)).inject(this)
        mPresenter.getData( mBinding.type)
    }
    override fun setMyLove(result: List<Music>) {
        var love = LocalMusicFragment.newInstance(Constants.MyLove);
        addFragments(R.id.layout_list, love)
        love.setList(result)
    }

    override fun setDownLoading(result: List<Music>) {
        var download = DownLoadingFragment.newInstance()
        addFragments(R.id.layout_list, download)
        download.setList(result)
    }

    override fun setDownLoad(result: List<Music>) {
        mymuisc.addAll(result)
        addFragments(R.id.layout_list, mymuiscfragment)
        mymuiscfragment.setList(mymuisc)
    }

    override fun setHistory(result: List<Music>) {
        var history = LocalMusicFragment.newInstance(Constants.History)
        addFragments(R.id.layout_list, history)
        history.setList(result)
    }

    override fun setMuMusic(result: List<Music>) {
        mymuisc.addAll(result)
        addFragments(R.id.layout_list, mymuiscfragment)
        mymuiscfragment.setList(mymuisc)
    }

    companion object {
        val TYPE = "TYPE"
        fun startActivity(context: Context, type: String) {
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra(TYPE, type)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
