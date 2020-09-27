package com.ymc.common.network.interceptor

/**
 * Created by ymc on 2020/9/27.
 * @Description
 */
interface IData<T> {

    fun id(): Int {
        return hashCode()
    }

    fun getCode(): String

    fun getMsg(): String?

    fun getResult(): T?

    fun isSuccess(): Boolean

}
