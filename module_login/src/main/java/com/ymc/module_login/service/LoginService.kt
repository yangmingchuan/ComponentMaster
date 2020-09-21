package com.ymc.module_login.service

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Created by ymc on 2020/9/21.
 * @Description login 对其他module暴露接口
 */
interface LoginService : IProvider {

    fun loginOut()

    val isLogin: Boolean

}