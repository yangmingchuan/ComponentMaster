package com.ymc.common.network

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ymc.common.network.exception.ResponseThrowable
import com.ymc.common.network.interceptor.IData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    /**
     * 核心方法
     */
    fun <T> launchOnlyResult(
        block: suspend CoroutineScope.() -> IData<T>,
        //成功
        success: (T) -> Unit = {},
        //错误 根据错误进行不同分类
        error: (Throwable) -> Unit = {
            //暂时关闭重连
//            reTry()
        },
        //完成
        complete: () -> Unit = {},
        //重试
        reTry: () -> Unit = {}
    ) {
        status.postValue(Status.LOADING)
        //正式请求接口
        launchUI {
            //异常处理
            handleException(
                //调用接口
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    //接口成功返回
                    executeResponse(res) {
                        success(it)
                    }
                },
                {
                    status.postValue(Status.ERROR)
                    //接口失败返回
                    error(it)
                },
                {
                    status.postValue(Status.COMPLETE)
                    //接口完成
                    complete()
                }
            )
        }
    }

    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
        response: IData<T>,
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            //接口成功返回后判断是否是增删改查成功，不满足的话，返回异常
            if (response.isSuccess()) {
                status.postValue(Status.SUCCESS)
                success(response.getResult()!!)
            } else {
                status.postValue(Status.ERROR)
                //状态码错误
                throw ResponseThrowable(
                    response.getCode(),
                    response.getMsg()!!
                )
            }
        }
    }

    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> IData<T>,
        success: suspend CoroutineScope.(IData<T>) -> Unit,
        error: suspend CoroutineScope.(Throwable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ){
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(e)
                e.printStackTrace()
            } finally {
                complete()
            }
        }
    }

}