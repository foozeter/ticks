package com.foureyedstraighthair.ticks

import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class UserLockBottomSheetBehavior<V : View>
    : BottomSheetBehavior<V> {

    var latestStableState = STATE_COLLAPSED; private set

    private var isLocked = true

    private var onSlide = {sheet: View, offset: Float -> }
    private var onStateChanged = { sheet: View, state: Int -> }

    private val innerCallback =
        object: BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(p0: View, p1: Float)
                    = this@UserLockBottomSheetBehavior.onSlide(p0, p1)

            override fun onStateChanged(p0: View, p1: Int) {

                when (p1) {
                    STATE_COLLAPSED,
                    STATE_EXPANDED,
                    STATE_HIDDEN -> latestStableState = p1
                }

                this@UserLockBottomSheetBehavior.onStateChanged(p0, p1)
            }
        }

    constructor(): super()

    constructor(
        context: Context,
        attrs: AttributeSet)
            : super(context, attrs)

    init {
        setBottomSheetCallback(innerCallback)
    }

    fun expand() {
        state = STATE_EXPANDED
    }

    fun collapse() {
        state = STATE_COLLAPSED
    }

    fun hide() {
        state = STATE_HIDDEN
    }

    fun isExpanded() = state == STATE_EXPANDED

    fun isCollapsed() = state == STATE_COLLAPSED

    fun isHidden() = state == STATE_HIDDEN

    fun isRecentlyExpanded() = latestStableState == STATE_EXPANDED

    fun isRecentlyCollapsed() = latestStableState == STATE_COLLAPSED

    fun isRecentlyHidden() = latestStableState == STATE_HIDDEN

    fun setOnSlideListener(listener: (sheet: View, offset: Float) -> Unit) {
        onSlide = listener
    }

    fun setOnStateChangedListener(listener: (sheet: View, state: Int) -> Unit) {
        onStateChanged = listener
    }

    fun lock() {
        isLocked = true
    }

    fun unlock() {
        isLocked = false
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent) =
        if (isLocked) false
        else super.onInterceptTouchEvent(parent, child, event)

    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent) =
        if (isLocked) false
        else super.onTouchEvent(parent, child, event)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int) =
        if (isLocked) false
        else super.onStartNestedScroll(
            coordinatorLayout, child, directTargetChild,
            target, nestedScrollAxes)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int) =
        if (isLocked) false
        else super.onStartNestedScroll(
            coordinatorLayout, child, directTargetChild,
            target, axes, type)

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray) {
        if (!isLocked) super.onNestedPreScroll(
            coordinatorLayout, child,
            target, dx, dy, consumed)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int) {
        if (!isLocked) super.onNestedPreScroll(
            coordinatorLayout, child, target,
            dx, dy, consumed, type)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View) {
        if (!isLocked) super.onStopNestedScroll(
            coordinatorLayout, child, target)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        type: Int) {
        if (!isLocked) super.onStopNestedScroll(
            coordinatorLayout, child, target, type)
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        velocityX: Float,
        velocityY: Float) =
        if (isLocked) false
        else super.onNestedPreFling(
            coordinatorLayout, child,
            target, velocityX, velocityY)

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun <V: View> from(view: V)
                = (view.layoutParams as CoordinatorLayout.LayoutParams)
            .behavior as UserLockBottomSheetBehavior<V>
    }
}