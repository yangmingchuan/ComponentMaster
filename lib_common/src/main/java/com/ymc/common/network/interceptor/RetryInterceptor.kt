package com.ymc.common.network.interceptor

import com.ymc.common.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * Created by ymc on 2020/9/29.
 * @Description 重试拦截器
 */
class RetryInterceptor(maxRetry: Int): Interceptor {
    private var retryNum = 0 //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    private var mMaxRetry = maxRetry

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < mMaxRetry) {
            retryNum++
            LogUtils.e("OkHttp 重试 num:$retryNum")
            response = chain.proceed(request)
        }
        return response
    }


}