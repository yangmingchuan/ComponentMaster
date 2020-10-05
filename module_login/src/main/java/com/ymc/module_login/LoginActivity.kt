package com.ymc.module_login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.ymc.common_base.base.BaseActivity
import com.ymc.common_base.arouter.ARouterConstant
import kotlinx.android.synthetic.main.login_activity_login.*

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
        var mViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        ivBack.setOnClickListener{finish()}
        tvLogin.setOnClickListener{
            //TODO 详细业务判断
            mViewModel.reqLogin(etPhone.text.toString(),etCode.text.toString())
        }
        mViewModel.user.observe(this, Observer{
            finish()
        })
    }

}