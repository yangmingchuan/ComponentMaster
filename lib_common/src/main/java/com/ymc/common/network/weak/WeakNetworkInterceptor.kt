package com.ymc.common.network.weak

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by ymc on 2020/9/24.
 * @Description 模拟弱网拦截器
 */
class WeakNetworkInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WeakNetworkManager.isActive) {
            val request = chain.request()
            return chain.proceed(request)
        }
        return when (WeakNetworkManager.type) {
            WeakNetworkManager.TYPE_TIMEOUT ->
                //超时
                WeakNetworkManager.simulateTimeOut(chain)
            WeakNetworkManager.TYPE_SPEED_LIMIT ->
                //限速
                WeakNetworkManager.simulateSpeedLimit(chain)
            else ->
                //断网
                WeakNetworkManager.simulateOffNetwork(chain)
        }
    }

}