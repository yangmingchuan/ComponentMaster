package com.ymc.common.adapter.base

/**
 * Item View 代理接口
 *
 * Author : ymc
 * Date   : 2020/9/14  10:03
 * Class  : ItemDelegate
 */
interface ItemDelegate<T> {

    /**
     * 布局文件
     */
    fun layoutId(): Int

    /**
     * 类型区分
     */
    fun isThisType(item: T, position: Int): Boolean

    /**
     * 数据处理
     */
    fun convert(holder: BaseViewHolder?, item: T, position: Int)

}