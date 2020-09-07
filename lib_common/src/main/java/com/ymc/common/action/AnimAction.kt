package com.ymc.common.action

/**
 * 动画样式
 *
 * Author : ymc
 * Date   : 2020/9/7  16:28
 * Class  : AnimAction
 */
interface AnimAction {
    companion object {
        /**
         * 默认动画效果
         */
        const val ANIM_DEFAULT = -1

        /**
         * 没有动画效果
         */
        const val ANIM_EMPTY = 0

        /**
         * 缩放+淡入淡出动画
         */
        val ANIM_SCALE: Int = com.ymc.common.R.style.Animation_Scale

        /**
         * 吐司动画
         */
        const val ANIM_TOAST = android.R.style.Animation_Toast

        /**
         * 上进上出
         */
        val ANIM_TOP: Int = com.ymc.common.R.style.Animation_TopToTop

        /**
         * 下进下出
         */
        val ANIM_BOTTOM: Int = com.ymc.common.R.style.Animation_BottomToBottom

        /**
         * 左进左出
         */
        val ANIM_LEFT: Int = com.ymc.common.R.style.Animation_LeftToLeft

        /**
         * 右进右出
         */
        val ANIM_RIGHT: Int = com.ymc.common.R.style.Animation_RightToRight

        /**
         * 左进右出
         */
        val ANIM_LEFT_TO_RIGHT: Int = com.ymc.common.R.style.Animation_LeftToRight

        /**
         * 右进左出
         */
        val ANIM_RIGHT_TO_LEFT: Int = com.ymc.common.R.style.Animation_RightToLeft
    }
}