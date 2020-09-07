package com.ymc.common

import android.content.Context
import com.ymc.common.utils.LogUtils
import com.ymc.common.utils.ToastUtils

/**
 * Author : ymc
 * Date   : 2020/9/7  17:04
 * Class  : ArchAction
 */

/**
 * toast
 *
 * @param text
 */
fun Context.toast(text: String) {
    ToastUtils.normal(text)
}

/**
 * log e
 *
 * @param text
 */
fun Context.loge(text: String) {
    LogUtils.e(text)
}

