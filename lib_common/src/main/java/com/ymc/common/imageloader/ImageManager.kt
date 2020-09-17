package com.ymc.common.imageloader

import android.content.Context
import android.widget.ImageView
import com.ymc.common.R

/**
 * Created by ymc on 2020/9/17.
 * @Description
 */
class ImageManager private constructor(){
    private var iImageLoder: IImageLoader? = null
    private var mContext: Context? = null

    init {
        iImageLoder = GlideImageLoader()
    }

    companion object{
        val instance: ImageManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ImageManager() }
    }

    /**
     * 全局初始化
     *
     * @param context ApplicaionContext
     */
    fun init(context: Context) {
        mContext = context
    }

    fun displayImage(url: Any?, imageView: ImageView?) {
        iImageLoder!!.displayImage(url, imageView!!, R.drawable.ic_image_default , R.drawable.ic_image_error)
    }

    fun displayImage(url: Any?, imageView: ImageView?, placeholder: Int, error: Int) {
        iImageLoder!!.displayImage(url, imageView!!, placeholder, error)
    }

    fun displayGif(url: Any?, imageView: ImageView?) {
        iImageLoder!!.displayGif(url, imageView!!)
    }

}