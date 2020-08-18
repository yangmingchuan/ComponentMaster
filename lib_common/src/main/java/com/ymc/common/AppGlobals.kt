package com.ymc.common

import android.annotation.SuppressLint
import android.app.Application
import java.lang.reflect.InvocationTargetException

/**
 * Author : ymc
 * Date   : 2020/8/17  17:32
 * Class  : AppGlobals
 */
class AppGlobals private constructor(){
    private var sApplication: Application? = null

    companion object {
        val instance: AppGlobals by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppGlobals() }
    }


    @SuppressLint("PrivateApi")
    fun getApplication(): Application? {
        if (sApplication == null) {
            try {
                sApplication = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null, null as Array<Any?>?) as Application
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
        return sApplication
    }
}