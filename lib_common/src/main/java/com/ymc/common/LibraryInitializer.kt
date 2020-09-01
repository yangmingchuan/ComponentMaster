package com.ymc.common

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import androidx.startup.Initializer
import com.ymc.common.startup.WidgetManager
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
        // 应用监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver)
        return WidgetManager
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return Collections.emptyList()
    }

}