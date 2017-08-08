package com.callanna.rankmusic.ui.fragment.mian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.callanna.rankmusic.R
import com.callanna.rankmusic.bean.Music
import com.callanna.rankmusic.dagger.compontent.ListMusicModule
import com.callanna.rankmusic.databinding.FragmentMeBinding
import com.callanna.rankmusic.mvp.contract.ListContract
import com.callanna.rankmusic.mvp.presenter.ListPresenter
import com.callanna.rankmusic.ui.activity.ListActivity
import com.callanna.rankmusic.ui.activity.base.BaseBingingFragment
import com.callanna.rankmusic.util.Constants
import com.callanna.rankmusic.util.getMainComponent
import com.callanna.rankmusic.util.runUI
import kotlinx.android.synthetic.main.fragment_me.*
import javax.inject.Inject

class MeFragment : BaseBingingFragment<FragmentMeBinding>(),ListContract.View{

    @Inject lateinit var mPresenter: ListPresenter
    private var mymusiccount: Int  = 0;
    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMeBinding {
        return FragmentMeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        context.getMainComponent().plus(ListMusicModule(this)).inject(this)
        with(mBinding!!){
            mBinding.btnLogin.setOnClickListener {

            }
            mBinding.imvLocalmusic.setOnClickListener {
                ListActivity.startActivity(context,Constants.MyMUSIC)
            }
            mBinding.imvDownloadmusic.setOnClickListener {
                ListActivity.startActivity(context,Constants.DownLoadING)
            }
            mBinding.imvHistorymusic.setOnClickListener {
                ListActivity.startActivity(context,Constants.History)
            }
            mBinding.layoutLove.setOnClickListener {
                ListActivity.startActivity(context,Constants.MyLove)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getData()
    }
    override fun setMyLove(result: List<Music>) {
        context.runUI {
            tv_lovecount.setText(result.size.toString())
        }
    }

    override fun setDownLoading(result: List<Music>) {
        context.runUI {
            imv_downloadmusic.setText(context.getString(R.string.downloadmusic) + "(" + result.size + ")")
        }
    }

    override fun setDownLoad(result: List<Music>) {
        mymusiccount += result.size;
        context.runUI {
            imv_localmusic.setText(context.getString(R.string.localmusic) + "(" + mymusiccount + ")")
        }
    }

    override fun setHistory(result: List<Music>) {
        context.runUI {
            imv_historymusic.setText(context.getString(R.string.historymusic) + "(" + result.size + ")")
        }
    }

    override fun setMuMusic(result: List<Music>) {
        mymusiccount += result.size;
        context.runUI{
            imv_localmusic.setText(context.getString(R.string.localmusic)+"("+mymusiccount+")")
        }

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
