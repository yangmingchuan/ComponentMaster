package com.ymc.common.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * Created by ymc on 2020/9/17.
 * @Description
 */
interface IImageLoader {

    /**
     * 初始化
     *
     * @param context 上下文
     */
    fun init(context: Context?)

    /**
     * 图片展示
     *
     * @param url       图片地址
     * @param imageView
     */
    fun displayImage(url: Any?, imageView: ImageView)

    /**
     * 带占位，错误图
     *
     * @param url         图片地址
     * @param imageView
     * @param placeholder 占位图
     * @param error       错误图
     */
    fun displayImage(url: Any?, imageView: ImageView, placeholder: Int, error: Int)

    /**
     * gif
     * @param url
     * @param imageView
     */
    fun displayGif(url: Any?, imageView: ImageView)

}