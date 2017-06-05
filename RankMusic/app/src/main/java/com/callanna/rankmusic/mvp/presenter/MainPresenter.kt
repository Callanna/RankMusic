package com.callanna.rankmusic.mvp.presenter

import android.util.Log
import com.callanna.rankmusic.mvp.BasePresenter
import com.callanna.rankmusic.mvp.contract.MainContract
import com.callanna.rankmusic.mvp.model.MainModel
import com.callanna.rankmusic.util.Constants
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by Callanna on 2017/6/4.
 */
class MainPresenter
    @Inject constructor(private val mModel:MainModel,
                        private val mView:MainContract.View):MainContract.Presenter, BasePresenter() {
    override fun getData(type: String) {
        if(type==Constants.ALL){
            getData(Constants.HOT_SONG)
            getData(Constants.UK)
            getData(Constants.LOCAL)
            getData(Constants.KOREA)
            getData(Constants.ROCK)
        }else {
            addSubscription(mModel.getData(type).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        res ->
                        when (type) {
                            Constants.HOT_SONG ->
                                mView.setHotSong(res)
                            Constants.ROCK ->
                                mView.setRock(res)
                            Constants.UK ->
                                mView.setUK(res)
                            Constants.LOCAL ->
                                mView.setLocal(res)
                            Constants.KOREA ->
                                mView.setKorea(res)
                            else ->
                                mView.setHotSong(res)
                        }
                    }, { e -> Log.e("duanyl", "error MainMusic:" + e.message) }))
        }
    }

}