package com.ymc.common.network.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.platform.Jdk9Platform.Companion.isAvailable

/**
 * Created by ymc on 2020/9/23.
 * @Description 缓存拦截器
 */
class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return if (isAvailable) {
            val response = chain.proceed(request)
            // 从缓存读取60秒
            val maxAge = 60
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            // 读取缓存信息
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
            val response = chain.proceed(request)
            // 设置缓存时间为3天
            val maxStale = 60 * 60 * 24 * 3
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }

}