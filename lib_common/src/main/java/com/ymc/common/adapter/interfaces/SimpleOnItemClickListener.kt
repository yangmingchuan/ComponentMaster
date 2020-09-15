package com.ymc.common.adapter.interfaces

import android.view.View
import com.ymc.common.adapter.base.BaseViewHolder

/**
 * Created by ymc on 2020/9/15.
 * @Description 常用点击事件
 */
class SimpleOnItemClickListener : OnItemClickListener{
    override fun onItemClick(view: View?, holder: BaseViewHolder?, position: Int) {
    }

    override fun onItemLongClick(view: View?, holder: BaseViewHolder?, position: Int): Boolean {
        return false
    }

    override fun onItemChildClick(view: View?, holder: BaseViewHolder?, position: Int) {
    }

    override fun onItemChildLongClick(
        view: View?,
        holder: BaseViewHolder?,
        position: Int
    ): Boolean {
        return false
    }
}