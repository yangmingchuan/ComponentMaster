package com.ymc.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by ymc on 2020/9/23.
 * @Description Header 拦截器
 */
class HeaderInterceptor(private val map: MutableMap<String, Any>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        // 通过这个标 识，用户所访问的网站可以显示不同的排版从而为用户提供更好的体验或者进行信息统计
        map["User-Agent"] = "Mozilla/5.0 (Android)"
//        map["Accept-Encoding"] = "gzip"
        map["Accept"] = "application/json"
        map["Content-Type"] = "application/json; charset=utf-8"
        for ((key, value) in map) {
            requestBuilder.addHeader(key, value as String)
        }
        requestBuilder.method(originalRequest.method, originalRequest.body)
        return chain.proceed(requestBuilder.build())
    }
}