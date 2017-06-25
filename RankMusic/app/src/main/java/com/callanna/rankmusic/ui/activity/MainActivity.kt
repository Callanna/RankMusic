package com.callanna.rankmusic.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.callanna.rankmusic.R
import com.callanna.rankmusic.databinding.ActivityMainBinding
import com.callanna.rankmusic.ui.activity.base.BaseBindingActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.callanna.rankmusic.ui.fragment.mian.HomeFragment
import com.callanna.rankmusic.ui.fragment.mian.MeFragment


class MainActivity : BaseBindingActivity<ActivityMainBinding>(){
    private var fragmentDdapter: FragmentAdapter? = null
    private val mFragmentsLists = ArrayList<Fragment>()


    override fun createDataBinding(savedInstanceState: Bundle?): ActivityMainBinding{

        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun initView(savedInstanceState: Bundle?) {
        with(mBinding!!){
            initFragmentData()
            fragmentDdapter = FragmentAdapter(supportFragmentManager, mFragmentsLists)
            viewpager.setAdapter(fragmentDdapter)
            viewpager.setCurrentItem(0)
            indicator.setViewPager(viewpager)
        }

    }
    private fun initFragmentData() {
        mFragmentsLists.add(HomeFragment.newInstance())
        mFragmentsLists.add(MeFragment.newInstance())
    }
    internal inner class FragmentAdapter(fm: FragmentManager, var lists: List<Fragment>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return lists[position]
        }

        override fun getCount(): Int {
            return lists.size
        }

        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

        fun setData(lists: List<Fragment>) {
            this.lists = lists
        }
    }

}
