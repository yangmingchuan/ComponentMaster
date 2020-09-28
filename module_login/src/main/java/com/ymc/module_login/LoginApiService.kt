package com.ymc.module_login

import com.ymc.common_base.base.BaseWanData
import com.ymc.module_login.bean.UserBean
import retrofit2.http.*

/**
 * Created by ymc on 2020/9/28.
 * @Description
 */
interface LoginApiService {

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): BaseWanData<UserBean>

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassword: String?
    ): BaseWanData<UserBean>


}