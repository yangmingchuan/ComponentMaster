package com.ymc.common.imageloader.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Created by ymc on 2020/9/17.
 * @Description Drawable 转换工具
 */
object DrawableTintUtil {

    /**
     * Drawable 颜色转化类
     *
     * @param drawable
     * @param color 资源
     * @return 改变颜色后的Drawable
     */
    fun tintDrawable(drawable: Drawable, color: Int): Drawable? {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(wrappedDrawable, color)
        return wrappedDrawable
    }


    /**
     * Drawable 颜色转化类
     *
     * @param drawable 源Drawable
     * @param colors
     * @return 改变颜色后的Drawable
     */
    fun tintListDrawable(drawable: Drawable, colors: ColorStateList?): Drawable? {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(wrappedDrawable, colors)
        return wrappedDrawable
    }

}