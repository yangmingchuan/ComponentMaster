package com.ymc.common.network.interceptor

import com.ymc.common.utils.LogUtils
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URLDecoder

/**
 * Created by ymc on 2020/9/23.
 * @Description log 拦截器
 */
class LoggingInterceptor : HttpLoggingInterceptor.Logger {

    /**
     * sb
     */
    private val mMessage = StringBuilder()


    override fun log(text: String) {
        try {
            var message = text
                .replace("\\+".toRegex(), "")
                .replace("%".toRegex(), "%%")
            message = message.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%%")
            message = message.replace("%%".toRegex(), "")
            message = message.replace("\\+".toRegex(), "")
            message = URLDecoder.decode(message, "utf-8")

            if (message.startsWith("--> POST")) {
                mMessage.setLength(0)
                mMessage.append(" ")
                mMessage.append("\r\n")
            }
            if (message.startsWith("--> GET")) {
                mMessage.setLength(0)
                mMessage.append(" ")
                mMessage.append("\r\n")
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if (message.startsWith("{") && message.endsWith("}") ||
                message.startsWith("[") && message.endsWith("]")) {
//                message = formatJson(message)
            }
            mMessage.append(message).append("\r\n")
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                LogUtils.e("\n${mMessage}\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}