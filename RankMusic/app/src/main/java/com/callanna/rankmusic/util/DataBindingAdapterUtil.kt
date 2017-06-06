package com.wingsofts.gankclient.ui

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.callanna.rankmusic.App
import com.callanna.rankmusic.R
import com.callanna.rankmusic.ui.customview.LoadingIndicatorView
import com.callanna.rankmusic.util.Constants
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Created by Callanna on 2017/5/26.
 */

@BindingAdapter("load_image")
fun loadImage(imageView: ImageView, url: String?) =
        Glide.with(imageView.context).load(url)
                .crossFade(1000)// 可设置时长，默认“300ms”
                .into(imageView)


@BindingAdapter("load_imageblur")
fun loadImageBlur(imageView: ImageView, url: String?) =
        Glide.with(imageView.context).load(url)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .bitmapTransform(BlurTransformation(App.instance, 23, 5))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView)

@BindingAdapter("load_asset")
fun loadAsset(imageView: ImageView, id: Int) =
        Glide.with(imageView.context).load(id).into(imageView)


@BindingAdapter("load_type")
        fun loadType(textview: TextView,type:String) =
        when(type){
          Constants.HOT_SONG->
              textview.text = App.instance.getText(R.string.text_hot_rank)
            Constants.UK->
                textview.text = App.instance.getText(R.string.text_uk)
            Constants.LOCAL->
                textview.text = App.instance.getText(R.string.text_local)
            Constants.ROCK->
                textview.text = App.instance.getText(R.string.text_rock)
            Constants.KOREA->
                textview.text = App.instance.getText(R.string.text_korea)
            else->
                textview.text = App.instance.getText(R.string.text_hot_rank)
         }
@BindingAdapter("visibility_tag")
     fun visibilityTag(view: LoadingIndicatorView, type:String) =
        if(type.equals(App.playCurrentType)) view.visibility = View.VISIBLE else view.visibility = View.INVISIBLE