package com.ymc.common.network

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Created by ymc on 2020/9/25.
 * @Description
 */
abstract class BaseViewModel : ViewModel(){

    val status = MutableLiveData<Status>()

    //重试的监听
    var listener: View.OnClickListener? = null

    val mScope : CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit){
        mScope.launch { block() }
    }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    //TODO 待添加请求方法

}