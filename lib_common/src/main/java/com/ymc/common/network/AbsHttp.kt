package com.ymc.common.network

import com.ymc.common.network.interceptor.CacheInterceptor
import com.ymc.common.network.interceptor.HeaderInterceptor
import com.ymc.common.network.interceptor.LoggingInterceptor
import com.ymc.common.network.interceptor.RetryInterceptor
import com.ymc.common.network.utils.GsonUtils.gson
import com.ymc.common.network.utils.SSLUtils
import com.ymc.common.network.utils.SSLUtils.getSslSocketFactory
import com.ymc.common.network.weak.WeakNetworkInterceptor
import com.ymc.common.startup.WidgetManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStream
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 * Created by ymc on 2020/9/23.
 * @Description Http
 */
abstract class AbsHttp {

    /**
     * baseUrl
     */
    abstract fun baseUrl(): String

    /**
     * Headers
     */
    open fun header(): HashMap<String, Any> = hashMapOf()

    /**
     * OkHttp的拦截器
     */
    open fun interceptors(): Iterable<Interceptor> = arrayListOf()

    /**
     * CallAdapter转换器
     */
    open fun callAdapterFactories(): Iterable<CallAdapter.Factory> = arrayListOf()

    /**
     * Converter转换器
     */
    open fun convertersFactories(): Iterable<Converter.Factory> = arrayListOf()

    /**
     * Https证书
     */
    open fun certificates(): Array<InputStream>? = arrayOf()

    /**
     * Https密钥
     */
    open fun keyStore(): InputStream? = null

    /**
     * Https密码
     */
    open fun keyStorePassword(): String? = null

    /**
     * 缓存
     */
    open fun saveCache(): Boolean = true

    /**
     * 开启打印
     */
    open fun openLog(): Boolean = true

    /**
     * 读
     */
    open fun readTimeout(): Long = 10L

    /**
     * 写
     */
    open fun writeTimeout(): Long = 10L

    /**
     * 请求
     */
    open fun connectTimeout(): Long = 10L

    /**
     * Retrofit
     */
    open fun retrofit(): Retrofit = defaultRetrofit()

    /**
     * OkHttpClient
     */
    open fun okHttpClient(): OkHttpClient = defaultOkHttpClient()

    /**
     * retrofit 构建
     */
    private fun defaultRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
        for (it in callAdapterFactories()) {
            builder.addCallAdapterFactory(it)
        }
        for (it in convertersFactories()) {
            builder.addConverterFactory(it)
        }
        builder.baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient())
        return builder.build()
    }

    /**
     * OkHttp 构建
     */
    private fun defaultOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (it in interceptors()) {
            builder.addInterceptor(it)
        }
        if (openLog()) {
            val logInterceptor = HttpLoggingInterceptor(LoggingInterceptor())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }
        if (saveCache()) {
            val externalCacheDir = WidgetManager.getContext().externalCacheDir
            if (null != externalCacheDir) {
                builder.cache(Cache(File(externalCacheDir.path + "/HttpCacheData"), 20 * 1024 * 1024))
                builder.addInterceptor(CacheInterceptor())
            }
        }
        val sslParams = getSslSocketFactory(keyStore(), keyStorePassword(), *certificates()!!)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        builder.addInterceptor(HeaderInterceptor(header()))
                //模拟弱网
            .addInterceptor(WeakNetworkInterceptor())
            .retryOnConnectionFailure(true)
                //设置重连次数 3次  （默认1次 + 2次）
            .addInterceptor(RetryInterceptor(2))
            .readTimeout(readTimeout(), TimeUnit.SECONDS)
            .writeTimeout(writeTimeout(), TimeUnit.SECONDS)
            .connectTimeout(connectTimeout(), TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
            .hostnameVerifier(SSLUtils.UnSafeHostnameVerifier)
        return builder.build()
    }

    /**
     * 创建
     */
    fun <T> create(clz: Class<T>): T {
        return retrofit().create(clz)
    }

}