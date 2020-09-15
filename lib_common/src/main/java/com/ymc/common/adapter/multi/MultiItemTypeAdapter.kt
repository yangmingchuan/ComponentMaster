package com.ymc.common.adapter.multi

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.ymc.common.adapter.base.BaseViewHolder
import com.ymc.common.adapter.base.ItemDelegateManager
import com.ymc.common.adapter.interfaces.ItemDelegate
import com.ymc.common.adapter.interfaces.OnItemClickListener
import com.ymc.common.adapter.interfaces.SpanSizeLookup

/**
 * 无Paging Adapter
 *
 * Author : ymc
 * Date   : 2020/9/11  15:35
 * Class  : MultiItemTypeAdapter
 */

open class MultiItemTypeAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    private var mData: List<T> = ArrayList()
    private val mItemDelegateManager: ItemDelegateManager<T> = ItemDelegateManager()
    private var mOnItemClickListener: OnItemClickListener? = null

    private var mSpanSizeLookup: SpanSizeLookup? = null

    private val mHeaders = SparseArray<View>()
    private val mFooters = SparseArray<View>()

    private var BASE_ITEM_TYPE_HEADER = 100000
    private var BASE_ITEM_TYPE_FOOTER = 200000

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
        val holder = BaseViewHolder.createViewHolder(parent.context, parent, layoutId)
        setListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (position < mHeaders.size() || position >= itemCount - mFooters.size()) {
            return
        }
        //列表中正常类型的itemView的 position 咱们需要减去添加headerView的个数
        if (getItem(position - mHeaders.size()) != null) {
            mItemDelegateManager.convert(
                holder,
                getItem(position - mHeaders.size()),
                holder.adapterPosition
            )
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val defSpanSizeLookup = manager.spanSizeLookup
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mSpanSizeLookup == null) {
                        if (isFixedViewType(position)) manager.spanCount else defSpanSizeLookup.getSpanSize(
                            position
                        )
                    } else {
                        if (isFixedViewType(position)) manager.spanCount else mSpanSizeLookup!!.getSpanSize(
                            manager, position - mHeaders.size()
                        )
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size + mHeaders.size() + mFooters.size()
    }

    /**
     * 获取 Item Type
     */
    override fun getItemViewType(position: Int): Int {
        if (position < mHeaders.size()) {
            //返回该position对应的headerview的  viewType
            return mHeaders.keyAt(position)
        }

        if (position >= itemCount - mFooters.size()) {
            //footer类型的，需要计算一下它的position实际大小
            return mFooters.keyAt(position - (itemCount - mFooters.size()))
        }
        return if (mItemDelegateManager.itemViewDelegateCount() <= 0) super.getItemViewType(position - mHeaders.size())
        else mItemDelegateManager.getItemViewType(
            getItem(position - mHeaders.size()), position - mHeaders.size()
        )
    }

    override fun registerAdapterDataObserver(observer: AdapterDataObserver) {
        super.registerAdapterDataObserver(AdapterDataObserverProxy(observer))
    }

    //如果我们先添加了headerView,而后网络数据回来了再更新到列表上
    //由于Paging在计算列表上item的位置时 并不会顾及我们有没有添加headerView，就会出现列表定位的问题
    //实际上 RecyclerView#setAdapter方法，它会给Adapter注册了一个AdapterDataObserver
    //咱么可以代理registerAdapterDataObserver()传递进来的observer。在各个方法的实现中，把headerView的个数算上，再中转出去即可
    inner class AdapterDataObserverProxy(private val mObserver: AdapterDataObserver) :
        AdapterDataObserver() {
        override fun onChanged() {
            mObserver.onChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mObserver.onItemRangeChanged(positionStart + mHeaders.size(), itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mObserver.onItemRangeChanged(positionStart + mHeaders.size(), itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mObserver.onItemRangeInserted(positionStart + mHeaders.size(), itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mObserver.onItemRangeRemoved(positionStart + mHeaders.size(), itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mObserver.onItemRangeMoved(
                fromPosition + mHeaders.size(),
                toPosition + mHeaders.size(), itemCount
            )
        }

    }

    /**
     * 新数据
     */
    open fun setDataItems(data: List<T>) {
        this.mData = data
        notifyDataSetChanged()
    }

    /**
     * 点击事件
     */
    private fun setListener(holder: BaseViewHolder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { v ->
                var position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                position -= mHeaders.size()
                mOnItemClickListener!!.onItemClick(v, holder, position)
            }
            holder.itemView.setOnLongClickListener { v ->
                var position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnLongClickListener false
                }
                position -= mHeaders.size()
                mOnItemClickListener!!.onItemLongClick(v, holder, position)
            }
        }
    }

    /**
     * 添加 Item 代理对象
     */
    open fun addItemDelegate(itemViewDelegate: ItemDelegate<T>): MultiItemTypeAdapter<T>? {
        mItemDelegateManager.addDelegate(itemViewDelegate)
        return this
    }

    open fun addItemDelegate(
        viewType: Int,
        itemViewDelegate: ItemDelegate<T>?
    ): MultiItemTypeAdapter<T>? {
        mItemDelegateManager.addDelegate(viewType, itemViewDelegate)
        return this
    }


    /**
     * 添加 头脚 部分
     */
    open fun addHeaderView(view: View?) {
        //判断给View对象是否还没有处在mHeaders数组里面
        if (mHeaders.indexOfValue(view) < 0) {
            mHeaders.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyDataSetChanged()
        }
    }

    open fun addFooterView(view: View?) {
        //判断给View对象是否还没有处在mFooters数组里面
        if (mFooters.indexOfValue(view) < 0) {
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyDataSetChanged()
        }
    }


    /**
     * 移除 头脚 部分
     */
    open fun removeHeaderView(view: View?) {
        val index = mHeaders.indexOfValue(view)
        if (index < 0) {
            return
        }
        mHeaders.removeAt(index)
        notifyDataSetChanged()
    }

    // 移除底部
    open fun removeFooterView(view: View?) {
        val index = mFooters.indexOfValue(view)
        if (index < 0) {
            return
        }
        mFooters.removeAt(index)
        notifyDataSetChanged()
    }

    open fun getItem(@IntRange(from = 0) position: Int): T {
        return if (position >= 0 && position < mData.size) {
            mData[position]
        } else {
            throw NullPointerException()
        }
    }

    open fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener?) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    protected open fun isFixedViewType(position: Int): Boolean {
        return position < mHeaders.size() || position >= itemCount - mFooters.size()
    }

    open fun setSpanSizeLookup(spanSizeLookup: SpanSizeLookup?) {
        mSpanSizeLookup = spanSizeLookup
    }
}