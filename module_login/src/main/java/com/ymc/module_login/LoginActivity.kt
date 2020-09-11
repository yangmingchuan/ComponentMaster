package com.ymc.module_login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.ymc.common_base.BaseActivity
import com.ymc.common_base.arouter.ARouterConstant

/**
 * 登陆界面
 */

@Route(path = ARouterConstant.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.login_activity_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

}