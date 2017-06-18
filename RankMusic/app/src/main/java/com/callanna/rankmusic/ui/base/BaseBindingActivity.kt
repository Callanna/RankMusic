package com.callanna.rankmusic.ui.activity.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.callanna.rankmusic.R
import com.zhy.autolayout.AutoLayoutActivity
import java.util.*


abstract class BaseBindingActivity<B : ViewDataBinding> : AutoLayoutActivity() {
    var mFragments: MutableMap<String, Fragment> = HashMap()
    private var fragmentTransaction: FragmentTransaction? = null


    lateinit var mBinding: B
    lateinit var context: Context
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStatuBar(this)
        context = this
        mBinding = createDataBinding(savedInstanceState)

        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun createDataBinding(savedInstanceState: Bundle?): B


    fun setupToolbar(toolbar: Toolbar) {
        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.icon_back)
        setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NewApi")
    protected fun setupStatuBar(activity: Activity) {
        if (Build.VERSION.SDK_INT == 19) {
            val window = activity.window
            val flags = window.attributes.flags
            if (flags or WindowManager.LayoutParams.FLAG_FULLSCREEN != flags) {
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val height = getStatusbarHeight(activity)
                val contentView = window
                        .findViewById(Window.ID_ANDROID_CONTENT)
                contentView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                contentView.setPadding(0, height, 0, 0)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val contentView = window
                        .findViewById(Window.ID_ANDROID_CONTENT)
                contentView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                contentView.setPadding(0, 0, 0, 0)
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    protected fun getStatusbarHeight(context: Context): Int {

        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            val y = context.resources.getDimensionPixelSize(x)
            return y
        } catch (e: Exception) {
            e.printStackTrace()
            return (context.resources.displayMetrics.density * 20 + 0.5).toInt()
        }

    }


    /**
     * 添加Fragments

     * @param id       要替换的View id
     * *
     * @param fragment 要添加的Fragment
     * *
     * @param tag      标记
     */
    fun addFragments(id: Int, fragment: Fragment?) {
        val tag = fragment?.javaClass?.simpleName
        if (tag == null|| TextUtils.isEmpty(tag) || fragment == null) {
            return
        }
        if (mFragments.get(tag) == null) {
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction?.add(id, fragment, tag)
            fragmentTransaction?.commitAllowingStateLoss()
            mFragments.put(tag, fragment)
        }
        //同时需要隐藏其他fragment
        showFragment(tag)
    }

    /**
     * 显示当前Fragment

     * @param tag 标记
     */
    fun showFragment(tag: String) {
        if (TextUtils.isEmpty(tag)) {
            return
        }
        for (fragmentkey in mFragments.keys) {
            fragmentTransaction = supportFragmentManager.beginTransaction()
            if (tag == fragmentkey) {
                fragmentTransaction?.show(mFragments.get(fragmentkey))
            } else {
                fragmentTransaction?.hide(mFragments.get(fragmentkey))
            }
            fragmentTransaction?.commitAllowingStateLoss()
        }
    }

    /**
     * 更换Fragment
     */
    fun replaceFragment(id: Int, fragment: Fragment, tag: String?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (tag == null || tag == "") {
            fragmentTransaction.replace(id, fragment)
        } else {
            fragmentTransaction.replace(id, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}