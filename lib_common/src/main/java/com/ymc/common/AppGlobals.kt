package com.ymc.common

import android.annotation.SuppressLint
import android.app.Application
import java.lang.reflect.InvocationTargetException

/**
 * Author : ymc
 * Date   : 2020/8/17  17:32
 * Class  : AppGlobals
 */
object AppGlobals {
    private var sApplication: Application? = null

    @JvmStatic
    @get:SuppressLint("PrivateApi")
    val application: Application
        get() {
            if (sApplication == null) {
                try {
                    sApplication = Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null) as Application
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
            return sApplication!!
        }
}