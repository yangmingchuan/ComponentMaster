package com.ymc.module_login

import androidx.lifecycle.MutableLiveData
import com.ymc.common.network.BaseViewModel
import com.ymc.common.utils.LogUtils
import com.ymc.common_base.base.BaseWanData
import com.ymc.common_base.config.Http
import com.ymc.module_login.bean.UserBean

/**
 * Created by ymc on 2020/9/27.
 * @Description 登录界面 vm
 */
class LoginViewModel : BaseViewModel() {
    private val TAG = "LoginViewModel"
    private val reqUser: MutableLiveData<UserBean> = MutableLiveData()
    private val mApi = Http.create(LoginApiService::class.java)

    @JvmField
    var userInfo = MutableLiveData<BaseWanData<UserBean>>()

    /**
     * 登录
     */
    fun reqLogin(username: String, paswd: String) {
        launchOnlyResult(
            block = { mApi.login(username = username, password = paswd) },
            reTry = { LogUtils.e("$TAG reqLogin username$username err") },
            success = { reqUser.postValue(it) },
            error = { LogUtils.e("$TAG reqLogin username$username err") },
            complete = { LogUtils.e("$TAG reqLogin complete") }
        )
    }
}