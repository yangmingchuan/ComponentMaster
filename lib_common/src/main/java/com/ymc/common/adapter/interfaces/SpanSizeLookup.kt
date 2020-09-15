package com.ymc.common.adapter.interfaces

import androidx.recyclerview.widget.GridLayoutManager

/**
 * Created by ymc on 2020/9/15.
 * @Description
 */
interface SpanSizeLookup {

    fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int

}