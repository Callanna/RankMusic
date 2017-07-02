package com.callanna.rankmusic.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.callanna.rankmusic.R
import com.callanna.rankmusic.databinding.ActivityListBinding
import com.callanna.rankmusic.ui.activity.base.BaseBindingActivity
import com.callanna.rankmusic.ui.fragment.DownLoadingFragment

class ListActivity : BaseBindingActivity<ActivityListBinding>() {
    override fun createDataBinding(savedInstanceState: Bundle?): ActivityListBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_list)
    }
    override fun initView(savedInstanceState: Bundle?) {
        addFragments(R.id.layout_list, DownLoadingFragment.newInstance())
    }

    companion object {
        val TYPE = "TYPE"
        fun startActivity(context: Context, type: String, position: Int = 0, view: View = ImageView(context)) {
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra(TYPE, type)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
