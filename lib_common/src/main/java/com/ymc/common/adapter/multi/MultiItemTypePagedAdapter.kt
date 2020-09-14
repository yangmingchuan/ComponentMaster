package com.ymc.common.adapter.multi

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ymc.common.adapter.base.BaseViewHolder
import com.ymc.common.adapter.base.ItemDelegate
import com.ymc.common.adapter.base.ItemDelegateManager

/**
 * Created by ymc on 2020/9/14.
 * @Description paging adapter
 */
class MultiItemTypePagedAdapter<T>(@NonNull diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, BaseViewHolder>(diffCallback) {

    private val mItemDelegateManager: ItemDelegateManager<T> = ItemDelegateManager()
    private var mOnItemClickListener: OnItemClickListener? = null

    private val mHeaders = SparseArray<View>()
    private val mFooters = SparseArray<View>()

    private var BASE_ITEM_TYPE_HEADER = 100000
    private var BASE_ITEM_TYPE_FOOTER = 200000

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (mHeaders.indexOfKey(viewType) >= 0) {
            val view = mHeaders[viewType]
            return BaseViewHolder(view)
        }

        if (mFooters.indexOfKey(viewType) >= 0) {
            val view = mFooters[viewType]
            return BaseViewHolder(view)
        }

        val itemViewDelegate: ItemDelegate<T>? = mItemDelegateManager.getItemViewDelegate(viewType)
        val layoutId: Int? = itemViewDelegate?.layoutId()
        val holder: BaseViewHolder = BaseViewHolder.createViewHolder(parent.context, parent, layoutId)
        setListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        var indexPos = position
        //列表中正常类型的itemView的 position 咱们需要减去添加headerView的个数
        indexPos -= mHeaders.size()
        getItem(indexPos)?.let {
            mItemDelegateManager.convert(
                holder, it, holder.adapterPosition
            )
        }
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= itemCount - mHeaders.size() - mFooters.size() + mHeaders.size()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < mHeaders.size()
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
     * 添加 HeaderView
     */
    fun addHeaderView(view: View?) {
        //判断给View对象是否还没有处在mHeaders数组里面
        if (mHeaders.indexOfValue(view) < 0) {
            mHeaders.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyDataSetChanged()
        }
    }

    /**
     * 添加 FooterView
     */
    fun addFooterView(view: View?) {
        //判断给View对象是否还没有处在mFooters数组里面
        if (mFooters.indexOfValue(view) < 0) {
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyDataSetChanged()
        }
    }

    /**
     * 移除 HeaderView
     */
    fun removeHeaderView(view: View?) {
        val index = mHeaders.indexOfValue(view)
        if (index < 0) {
            return
        }
        mHeaders.removeAt(index)
        notifyDataSetChanged()
    }

    /**
     * 移除 FooterView
     */
    fun removeFooterView(view: View?) {
        val index = mFooters.indexOfValue(view)
        if (index < 0) {
            return
        }
        mFooters.removeAt(index)
        notifyDataSetChanged()
    }


    /**
     * Item 点击接口
     */
    interface OnItemClickListener {
        fun onItemClick(
            view: View?,
            holder: BaseViewHolder?,
            position: Int
        )

        fun onItemLongClick(
            view: View?,
            holder: BaseViewHolder?,
            position: Int
        ): Boolean

        fun onItemChildClick(
            view: View?,
            holder: BaseViewHolder?,
            position: Int
        )

        fun onItemChildLongClick(
            view: View?,
            holder: BaseViewHolder?,
            position: Int
        ): Boolean
    }

}