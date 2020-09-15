package com.ymc.common.adapter.multi

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import java.util.*

/**
 * Created by ymc on 2020/9/15.
 * @Description
 *  一个可变更的ItemKeyedDataSource 数据源
 * <p>
 * 工作原理是：我们知道DataSource是会被PagedList 持有的。
 * 一旦，我们调用了new PagedList.Builder<Key, Value>().build(); 那么就会立刻触发当前DataSource的loadInitial()方法，而且是同步
 * 详情见ContiguousPagedList的构造函数,而我们在当前DataSource的loadInitial()方法中返回了 最新的数据集合 data。
 * 一旦，我们再次调用PagedListAdapter#submitList()方法 就会触发差分异计算 把新数据变更到列表之上了。
 */
class MutablePageKeyedDataSource<Value> : PageKeyedDataSource<Int, Value>() {

    var data: List<Value> = ArrayList()

    fun buildNewPagedList(config: PagedList.Config?): PagedList<Value>? {
        return PagedList.Builder(this, config!!)
            .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
            .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
            .build()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Value>
    ) {
        callback.onResult(data, null, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        callback.onResult(emptyList(), null)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        callback.onResult(emptyList(), null)
    }
}