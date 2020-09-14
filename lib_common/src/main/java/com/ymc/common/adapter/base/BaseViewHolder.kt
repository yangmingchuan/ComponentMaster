package com.ymc.common.adapter.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Base ViewHolder
 *
 * Author : ymc
 * Date   : 2020/9/11  15:36
 * Class  : BaseViewHolder
 */

class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var convertView: View? = itemView

    private val mViews: SparseArray<View>? = SparseArray()

    companion object {
        fun createViewHolder(context: Context?, parent: ViewGroup, layoutId: Int?): BaseViewHolder {
            val itemView = layoutId?.let {
                LayoutInflater.from(context).inflate(
                    it, parent, false
                )
            }
            return BaseViewHolder(itemView!!)
        }
    }

}