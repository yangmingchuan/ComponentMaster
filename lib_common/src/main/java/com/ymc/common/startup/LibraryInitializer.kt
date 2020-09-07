package com.ymc.common.startup

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import androidx.startup.Initializer
import com.ymc.common.utils.AppLifeObserver
import com.ymc.common.utils.LogUtils
import java.util.*

/**
 * Author : ymc
 * Date   : 2020/8/18  10:34
 * Class  : LibraryInitializer
 */

class LibraryInitializer : Initializer<WidgetManager> {

    override fun create(context: Context): WidgetManager {
        // 突破65535的限制
        MultiDex.install(context)
        // log初始化
        LogUtils.init(LogUtils.LogLevel.DEBUG)
        // 监听app前后台
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver)
        return WidgetManager
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return Collections.emptyList()
    }

}