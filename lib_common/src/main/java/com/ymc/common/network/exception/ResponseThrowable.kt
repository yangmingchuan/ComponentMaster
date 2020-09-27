package com.ymc.common.network.exception

import java.lang.Exception

/**
 * Created by ymc on 2020/9/27.
 * @Description 自定义错误类总结
 */

class ResponseThrowable : Exception {
    var code: String
    var errMsg: String

    constructor(mcode: String, merrMsg: String, e: Throwable? = null) : super(e) {
        code = mcode
        errMsg = merrMsg
    }

}