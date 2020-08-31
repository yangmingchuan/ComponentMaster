package com.ymc.common

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import com.ymc.common.utils.LogUtils

/**
 * Author : ymc
 * Date   : 2020/8/18  10:34
 * Class  : LibraryInitializer
 */

class LibraryInitializer : ContentProvider(){

    /**
     * 初始化
     */
    override fun onCreate(): Boolean {
        LogUtils.init(if (BuildConfig.DEBUG) LogUtils.LogLevel.ERROR else LogUtils.LogLevel.NONE)
        // 突破65535的限制
        MultiDex.install(AppGlobals.application)
        // 应用监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver())
        return true
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}