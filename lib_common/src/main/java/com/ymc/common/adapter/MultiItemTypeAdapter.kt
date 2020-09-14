package com.ymc.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ymc.common.adapter.base.BaseViewHolder

/**
 * 自定义 Adapter
 *
 * Author : ymc
 * Date   : 2020/9/11  15:35
 * Class  : MultiItemTypeAdapter
 */

open class MultiItemTypeAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    }

    override fun getItemCount(): Int {
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    }

}