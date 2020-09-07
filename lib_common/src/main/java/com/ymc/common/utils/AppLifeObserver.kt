package com.ymc.common.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Author : ymc
 * Date   : 2020/8/18  10:53
 * Class  : AppLifeObserver
 */

object AppLifeObserver : LifecycleObserver{
    /**
     * ON_CREATE 在应用程序的整个生命周期中只会被调用一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogUtils.e("应用首次创建")
    }

    /**
     * 应用程序出现到前台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        LogUtils.e("应用进入前台")
    }

    /**
     * 应用程序退出到后台时调用
     * 会有一定的延后，因为系统需要为屏幕旋转，配置发生变化导致Activity重新创建预留一些时间
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        LogUtils.e("应用进入后台")
    }

    /**
     * 永远不会被调用到，系统不会分发调用ON_DESTROY事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtils.e("应用被杀死")
    }
}