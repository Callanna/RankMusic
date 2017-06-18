package com.callanna.rankmusic.ui.activity.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseBingingFragment<B:ViewDataBinding> : Fragment(){
    private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
     lateinit var mBinding : B
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = createDataBinding(inflater,container,savedInstanceState)
        initView()
        return mBinding.root

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //状态恢复
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //状态保存，防止Show和hide恢复混乱
        outState!!.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    abstract fun  createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): B

    abstract fun initView()
    /**
     * 更换Fragment
     */
    fun replaceFragment(id: Int, fragment: Fragment, tag: String?) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (tag == null || tag == "") {
            fragmentTransaction.replace(id, fragment)
        } else {
            fragmentTransaction.replace(id, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * 替换该Fragment内部的layout显示为fragment
     */
    fun replaceChildFragment(id: Int, fragment: Fragment, tag: String?) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        if (tag == null || tag == "") {
            fragmentTransaction.replace(id, fragment)
        } else {
            fragmentTransaction.replace(id, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * 退出当前Fragment内部的Fragment
     */
    protected fun finishChild() {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
        }
    }
    /**
     * 返回，退出当前Fragment

     * @author YOLANDA
     */
    fun finish() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        }
    }
}