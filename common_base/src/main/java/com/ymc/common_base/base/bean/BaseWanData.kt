package com.ymc.common_base.base.bean

import com.ymc.common.network.interceptor.IData
import java.io.Serializable

/**
 * Created by ymc on 2020/9/28.
 * @Description 玩android 基类
 */

data class BaseWanData<T>(var errorCode: Int,var errorMsg: String,var data: T) :IData<T> ,Serializable{

    override fun getCode(): String {
        return errorCode.toString()
    }

    override fun getMsg(): String? {
        return errorMsg
    }

    override fun getResult(): T? {
        return data
    }

    override fun isSuccess(): Boolean = errorCode ==0

}