package com.ymc.common.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ymc.common.R
import java.io.File

/**
 * Created by ymc on 2020/9/17.
 * @Description glide图片加载
 */

class GlideImageLoader : IImageLoader {

    override fun init(context: Context?) {
    }


    override fun displayImage(url: Any?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .apply(initCommonRequestOption())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    override fun displayImage(url: Any?, imageView: ImageView, placeholder: Int, error: Int) {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(placeholder) //占位图
            .error(error) //错误图
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(imageView.context)
            .load(url)
            .apply(options)
            .into(imageView)
    }

    override fun displayGif(url: Any?, imageView: ImageView) {
        val options = RequestOptions()
        Glide.with(imageView.context)
            .load(url)
            .apply(options)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(imageView)
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     */
    fun loadCircleImage(url: String?, imageView: ImageView) {
        val requestOptions = RequestOptions()
            .priority(Priority.HIGH)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    /**
     * 加载圆角图片
     *
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    fun loadRoundImage(url: String?, imageView: ImageView, radius: Int) {
        val requestOptions = RequestOptions()
            .priority(Priority.HIGH)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transforms(CenterCrop(), RoundedCorners(radius))
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    /**
     * 加载图片指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    fun loadSizeImage(
        context: Context?,
        url: String?,
        imageView: ImageView?,
        width: Int,
        height: Int
    ) {
        val requestOptions = RequestOptions()
            .priority(Priority.HIGH)
            .override(width, height)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(context!!)
            .load(url)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView!!)
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    fun loadFileImage(context: Context?, file: File?, imageView: ImageView?) {
        val requestOptions = RequestOptions()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
        Glide.with(context!!)
            .load(file)
            .apply(requestOptions)
            .into(imageView!!)
    }

    private fun initCommonRequestOption(): RequestOptions {
        val options = RequestOptions()
        options.placeholder(R.drawable.ic_image_default)
            .error(R.drawable.ic_image_error)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false)
            .priority(Priority.NORMAL)
        return options
    }

}