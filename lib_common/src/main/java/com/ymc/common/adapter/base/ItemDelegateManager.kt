package com.ymc.common.adapter.base

import androidx.collection.SparseArrayCompat

/**
 * ItemView 管理
 *
 * Author : ymc
 * Date   : 2020/9/14  10:02
 * Class  : ItemDelegateManager
 */

class ItemDelegateManager<T>{

    private val delegates = SparseArrayCompat<ItemDelegate<T>>()

    fun itemViewDelegateCount(): Int {
        return delegates.size()
    }

    fun addDelegate(delegate: ItemDelegate<T>?): ItemDelegateManager<T>? {
        var viewType = delegates.size()
        delegates.put(viewType, delegate)
        viewType++
        return this
    }

    fun addDelegate(viewType: Int, delegate: ItemDelegate<T>?): ItemDelegateManager<T>? {
        require(delegates[viewType] == null) {
            ("An ItemDelegate is already registered for the viewType = "
                    + viewType
                    + ". Already registered ItemDelegate is "
                    + delegates[viewType])
        }
        delegates.put(viewType, delegate)
        return this
    }

    fun getItemViewType(item: T, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i in delegatesCount - 1 downTo 0) {
            val delegate = delegates.valueAt(i)
            if (delegate.isThisType(item, position)) {
                return delegates.keyAt(i)
            }
        }
        throw IllegalArgumentException(
            "No ItemDelegate added that matches position= $position in data source"
        )
    }

    fun convert(holder: BaseViewHolder?, item: T, position: Int) {
        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate = delegates.valueAt(i)
            if (delegate.isThisType(item, position)) {
                delegate.convert(holder, item, position)
                return
            }
        }
        throw IllegalArgumentException(
            "No ItemDelegateManager added that matches position= $position in data source"
        )
    }

    fun getItemViewDelegate(viewType: Int): ItemDelegate<T>? {
        return delegates[viewType]
    }

    fun getItemLayoutId(viewType: Int): Int {
        return getItemViewDelegate(viewType)!!.layoutId()
    }

    fun getItemViewType(itemViewDelegate: ItemDelegate<T>?): Int {
        return delegates.indexOfValue(itemViewDelegate)
    }
}