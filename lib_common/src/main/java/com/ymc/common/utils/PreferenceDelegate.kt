package com.ymc.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Shared Preferences  保存读取工具类
 * @author ymc
 */

class PreferencesUtil<T> (private val key: String, private val value: T) :ReadWriteProperty<Any?,T>{

    companion object {
        lateinit var preferences : SharedPreferences

        /**
         * 初始化 sp
         */
        fun attch(context : Context){
            preferences = context.getSharedPreferences(context.packageName + "xx",
                Context.MODE_PRIVATE)
        }

        fun clear(){
            preferences.edit().clear().apply()
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return  findPreference(key, value)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putPreference(key, value)

    /**
     * 获取 sp value
     * 多次使用到 preferences 我们可以使用with 抽出来
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> findPreference(name: String, default: T): T = preferences.run {
        val res: Any = when (default){
            is Long -> getLong(name,default)
            is String -> getString(name, default) as String
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("get sp type error")
        }
        return res as T
    }

    /**
     *  添加 sp value
     */
    @SuppressLint("CommitPrefEdits")
    private fun <T> putPreference(key: String, value: T)  = with(preferences.edit()){
        when (value){
            is Long -> putLong(key,value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException("put sp type error")
        }.apply()
    }
}