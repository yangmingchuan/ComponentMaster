package com.ymc.common.interfaces

import android.os.Bundle

/**
 * Author : ymc
 * Date   : 2020/8/31  15:32
 * Class  : IView
 */

interface IView : ILifecycle {
    /**
     * 初始化布局
     */
    fun getLayoutId(): Int {
        return 0
    }

    /**
     * 初始化控制、监听等轻量级操作
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 处理重量级数据、逻辑
     */
    abstract fun initViewData()

}