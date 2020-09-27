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


}