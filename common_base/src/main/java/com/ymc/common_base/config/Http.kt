package com.ymc.common_base.config

import com.ymc.common.network.AbsHttp

/**
 * Created by ymc on 2020/9/27.
 * @Description http 工具类
 */

object Http : AbsHttp(){

    override fun baseUrl(): String {
        return Constants.URL_WAN_ANDROID
    }

    /**
     * 如果使用缓存 部分接口容易出现 504
     */
    override fun saveCache(): Boolean {
        return false
    }

}