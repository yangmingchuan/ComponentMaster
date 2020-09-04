package com.ymc.common_base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import com.ymc.common.startup.WidgetManager

/**
 * 单模块运行使用， 仅作为独立模块运行时初始化数据
 *
 * Author : ymc
 * Date   : 2020/9/4  14:13
 * Class  : ComApplication
 */

open class ComApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化阿里路由框架
        if (WidgetManager.isDebug) {
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this)
        MMKV.initialize(this)

    }

}