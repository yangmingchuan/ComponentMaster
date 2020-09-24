package com.ymc.common.network.weak

import android.os.SystemClock
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by ymc on 2020/9/24.
 * @Description 弱网管理
 */
object WeakNetworkManager {

    // 无网络
    const val TYPE_OFF_NETWORK = 0

    // 超时
    const val TYPE_TIMEOUT = 1

    // 限速
    const val TYPE_SPEED_LIMIT = 2

    // 超时
    const val DEFAULT_TIMEOUT_MILLIS = 2000

    // 限速 请求
    const val DEFAULT_REQUEST_SPEED = 1

    // 响应
    const val DEFAULT_RESPONSE_SPEED = 1

    var type = TYPE_OFF_NETWORK
    var timeOutMillis = DEFAULT_TIMEOUT_MILLIS.toLong()
        private set
    var requestSpeed = DEFAULT_REQUEST_SPEED.toLong()
        private set
    var responseSpeed = DEFAULT_RESPONSE_SPEED.toLong()
        private set
    private val mIsActive = AtomicBoolean(false)

    // 是否开启
    var isActive: Boolean
        get() = mIsActive.get()
        set(isActive) {
            mIsActive.set(isActive)
        }

    /**
     * 模拟断网
     */
    @Throws(IOException::class)
    fun simulateOffNetwork(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val responseBody = ResponseBody.create(response.body!!.contentType(), "")
        return response.newBuilder()
            .code(400)
            .message(String.format("Unable to resolve host %s: No address associated with hostname", chain.request().url.host))
            .body(responseBody)
            .build()
    }

    /**
     * 模拟超时
     *
     * @param chain url
     */
    @Throws(IOException::class)
    fun simulateTimeOut(chain: Interceptor.Chain): Response {
        SystemClock.sleep(timeOutMillis)
        val response = chain.proceed(chain.request())
        val responseBody = ResponseBody.create(response.body!!.contentType(), "")
        return response.newBuilder()
            .code(400)
            .message(String.format("failed to connect to %s  after %dms", chain.request().url.host, timeOutMillis))
            .body(responseBody)
            .build()
    }

    /**
     * 限速
     */
    @Throws(IOException::class)
    fun simulateSpeedLimit(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val body = request.body
        if (body != null) {
            //大于0使用限速的body 否则使用原始body
            val requestBody: RequestBody = if (requestSpeed > 0) SpeedLimitRequestBody(requestSpeed, body) else body
            request = request.newBuilder().method(request.method, requestBody).build()
        }
        val response = chain.proceed(request)
        //大于0使用限速的body 否则使用原始body
        val responseBody = response.body
        val newResponseBody = if (responseSpeed > 0) SpeedLimitResponseBody(responseSpeed, responseBody) else responseBody!!
        return response.newBuilder().body(newResponseBody).build()
    }


}