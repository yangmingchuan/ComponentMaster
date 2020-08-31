package com.ymc.common.utils

import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout

/**
 * Author : ymc
 * Date   : 2020/8/17  17:01
 * Class  : KeyboardUtils
 */

object KeyboardUtils {
    private var sContentViewInvisibleHeightPre = 0
    private var onGlobalLayoutListener: OnGlobalLayoutListener? = null
    private var onSoftInputChangedListener: OnSoftInputChangedListener? = null

    /**
     * Show the soft input.
     *
     * @param activity The activity.
     */
    @JvmStatic
    fun showSoftInput(activity: Activity) {
        getHandler().postDelayed(Runnable {
            val imm =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    ?: return@Runnable
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
                view.isFocusable = true
                view.isFocusableInTouchMode = true
                view.requestFocus()
            }
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }, 500)


    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    fun showSoftInput(context: Context, view: View) {
        getHandler().postDelayed(Runnable {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ?: return@Runnable
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }, 500)
    }

    private var handler: Handler? = null

    private fun getHandler(): Handler {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        return handler as Handler
    }

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    fun hideSoftInput(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ?: return
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        try {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) { // DeadSystemException
        }
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    fun hideSoftInput(context: Context, view: View) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ?: return
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Toggle the soft input display or not.
     */
    fun toggleSoftInput(context: Context) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ?: return
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Return whether soft input is visible.
     *
     * The minimum height is 200
     *
     * @param activity The activity.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isSoftInputVisible(activity: Activity): Boolean {
        return isSoftInputVisible(
            activity,
            200
        )
    }

    /**
     * Return whether soft input is visible.
     *
     * @param activity             The activity.
     * @param minHeightOfSoftInput The minimum height of soft input.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isSoftInputVisible(
        activity: Activity,
        minHeightOfSoftInput: Int
    ): Boolean {
        return getContentViewInvisibleHeight(
            activity
        ) >= minHeightOfSoftInput
    }

    private fun getContentViewInvisibleHeight(activity: Activity): Int {
        val contentView =
            activity.findViewById<View>(R.id.content) as FrameLayout
        val contentViewChild = contentView.getChildAt(0)
        val outRect = Rect()
        contentViewChild.getWindowVisibleDisplayFrame(outRect)
        Log.d(
            "KeyboardUtils", "getContentViewInvisibleHeight: "
                    + (contentViewChild.bottom - outRect.bottom)
        )
        return contentViewChild.bottom - outRect.bottom
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    fun registerSoftInputChangedListener(
        activity: Activity,
        listener: OnSoftInputChangedListener?
    ) {
        val flags = activity.window.attributes.flags
        if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        val contentView =
            activity.findViewById<View>(R.id.content) as FrameLayout
        sContentViewInvisibleHeightPre =
            getContentViewInvisibleHeight(
                activity
            )
        onSoftInputChangedListener = listener
        onGlobalLayoutListener = OnGlobalLayoutListener {
            if (onSoftInputChangedListener != null) {
                val height =
                    getContentViewInvisibleHeight(
                        activity
                    )
                if (sContentViewInvisibleHeightPre != height) {
                    onSoftInputChangedListener!!.onSoftInputChanged(height)
                    sContentViewInvisibleHeightPre = height
                }
            }
        }
        contentView.viewTreeObserver
            .addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun unregisterSoftInputChangedListener(activity: Activity) {
        val contentView =
            activity.findViewById<View>(R.id.content)
        contentView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        onSoftInputChangedListener = null
        onGlobalLayoutListener = null
    }


    interface OnSoftInputChangedListener {
        fun onSoftInputChanged(height: Int)
    }
}