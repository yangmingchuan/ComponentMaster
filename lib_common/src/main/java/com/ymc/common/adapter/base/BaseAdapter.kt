package com.ymc.common.adapter.base

import com.ymc.common.adapter.interfaces.ItemDelegate
import com.ymc.common.adapter.multi.MultiItemTypeAdapter

/**
 * Author : ymc
 * Date   : 2020/9/11  15:34
 * Class  : BaseAdapter
 */

abstract class BaseAdapter<T> : MultiItemTypeAdapter<T>() {

    protected var mLayoutId = 0

    protected abstract fun bind(holder: BaseViewHolder?, item: T?, position: Int)

    open fun BaseAdapter(mLayoutId: Int) {
        this.mLayoutId = mLayoutId
        addItemDelegate(object : ItemDelegate<T> {
            override fun layoutId(): Int {
                return mLayoutId
            }

            override fun isThisType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(holder: BaseViewHolder?, item: T, position: Int) {
                bind(holder, item, position)
            }
        })
    }

}