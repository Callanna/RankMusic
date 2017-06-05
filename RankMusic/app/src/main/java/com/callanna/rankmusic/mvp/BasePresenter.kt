package com.callanna.rankmusic.mvp

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Callanna on 2017/5/26.
 */
open class BasePresenter {
    var compositeSubscription = CompositeSubscription()

    protected fun addSubscription(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    fun unSubscribe() {
        if(compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe()
        }
    }
}