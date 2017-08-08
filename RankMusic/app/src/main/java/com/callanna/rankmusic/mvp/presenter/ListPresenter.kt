package com.callanna.rankmusic.mvp.presenter

import android.util.Log
import com.callanna.rankmusic.mvp.BasePresenter
import com.callanna.rankmusic.mvp.contract.ListContract
import com.callanna.rankmusic.mvp.model.MainModel
import com.callanna.rankmusic.util.Constants
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by Callanna on 2017/6/4.
 */
class ListPresenter
    @Inject constructor(private val mModel:MainModel,
                        private val mView: ListContract.View):ListContract.Presenter, BasePresenter() {
    override fun getData(type: String) {
        if (type == Constants.ALL) {
            getData(Constants.DownLoad)
            getData(Constants.DownLoadING)
            getData(Constants.MyLove)
            getData(Constants.MyMUSIC)
            getData(Constants.History)
        } else {
            addSubscription(when (type) {
                Constants.DownLoad ->
                    mModel.getDownLoaded()
                Constants.DownLoadING ->
                    mModel.getDownLoading()
                Constants.MyLove ->
                    mModel.getMyLove()
                Constants.MyMUSIC ->
                    mModel.getMyMusic()
                Constants.History ->
                    mModel.getHistory()
                else ->
                    mModel.getMyMusic()

            }.observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        res ->
                        when (type) {
                            Constants.DownLoad ->
                                mView.setDownLoad(res)
                            Constants.DownLoadING ->
                                mView.setDownLoading(res)
                            Constants.MyMUSIC ->
                                mView.setMuMusic(res)
                            Constants.MyLove ->
                                mView.setMyLove(res)
                            Constants.History ->
                                mView.setHistory(res)
                            else ->
                                mView.setMuMusic(res)
                        }
                    }, { e -> Log.e("duanyl", "error MainMusic:" + e.message) }))
        }
    }


}