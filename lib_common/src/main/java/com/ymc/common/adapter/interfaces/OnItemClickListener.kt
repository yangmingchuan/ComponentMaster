package com.ymc.common.adapter.interfaces

import android.view.View
import com.ymc.common.adapter.base.BaseViewHolder

/**
 * Created by ymc on 2020/9/15.
 * @Description adapter item click listener
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