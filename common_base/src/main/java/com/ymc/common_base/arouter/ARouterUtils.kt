package com.ymc.common_base.arouter

import com.alibaba.android.arouter.launcher.ARouter

/**
 * 路由跳转工具类
 *
 * Author : ymc
 * Date   : 2020/9/10  18:50
 * Class  : ARouterUtils
 */

object ARouterUtils {

    /**
     * 获取fragment
     */
    @JvmStatic
    fun startFragment(path: String?) {
        ARouter.getInstance().build(path).navigation()
    }

    @JvmStatic
    fun startActivity(path: String?) {
        ARouter.getInstance().build(path).navigation()
    }

}