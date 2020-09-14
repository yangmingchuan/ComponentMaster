package com.ymc.common.adapter.multi

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ymc.common.adapter.base.BaseViewHolder
import com.ymc.common.adapter.base.ItemDelegate
import com.ymc.common.adapter.base.ItemDelegateManager
import java.util.*

/**
 * 自定义 Adapter
 *
 * Author : ymc
 * Date   : 2020/9/11  15:35
 * Class  : MultiItemTypeAdapter
 */

open class MultiItemTypeAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    protected var mData: List<T> = ArrayList()
    private val mItemDelegateManager: ItemDelegateManager<T> = ItemDelegateManager()
    private val mOnItemClickListener: MultiItemTypePagedAdapter.OnItemClickListener? = null

    private val mHeaders = SparseArray<View>()
    private val mFooters = SparseArray<View>()

    private val BASE_ITEM_TYPE_HEADER = 100000
    private val BASE_ITEM_TYPE_FOOTER = 200000

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (mHeaders.indexOfKey(viewType) >= 0) {
            val view: View = mHeaders.get(viewType)
            return BaseViewHolder(view)
        }

        if (mFooters.indexOfKey(viewType) >= 0) {
            val view: View = mFooters.get(viewType)
            return BaseViewHolder(view)
        }

        val itemViewDelegate: ItemDelegate<T>? = mItemDelegateManager.getItemViewDelegate(viewType)
        val layoutId: Int? = itemViewDelegate?.layoutId()
        val holder =
            BaseViewHolder.createViewHolder(parent.context, parent, layoutId)
        onViewHolderCreated(holder, parent, viewType)
        setListener(holder)
        return holder

    }

    override fun getItemCount(): Int {
        return mData.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }

}