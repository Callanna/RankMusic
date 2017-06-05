package com.wingsofts.gankclient.ui

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.callanna.rankmusic.App
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
                .bitmapTransform(BlurTransformation(App.instance, 23, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView)

@BindingAdapter("load_asset")
fun loadAsset(imageView: ImageView, id: Int) =
        Glide.with(imageView.context).load(id).into(imageView)
