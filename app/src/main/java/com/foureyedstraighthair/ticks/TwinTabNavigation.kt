package com.foureyedstraighthair.ticks

import android.content.Context
import android.support.transition.AutoTransition
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.twin_tab_navigation_bar.view.*

class TwinTabNavigation(
    context: Context, attrs: AttributeSet)
    : LinearLayout(context, attrs) {

    companion object {
        private const val DESIRED_HEIGHT = 56 // dp
    }

    private var selectedTabColor = 0

    private var unselectedTabColor = 0

    private var leftTabIsSelected = true

    private var isAnimating = false

    private var onTabClickListener = {_: Boolean -> }

    private val transition = TransitionSet().apply {

        val auto = AutoTransition()
        val changeTint = Recolor()

        addTransition(auto)
        addTransition(changeTint)

        ordering = TransitionSet.ORDERING_TOGETHER
        auto.ordering = AutoTransition.ORDERING_TOGETHER
        changeTint.duration = auto.duration

        addListener(object: Transition.TransitionListener {

            override fun onTransitionStart(p0: Transition) {
                isAnimating = true
            }

            override fun onTransitionEnd(p0: Transition) {
                isAnimating = false
                leftTabIsSelected = !leftTabIsSelected
            }

            override fun onTransitionCancel(p0: Transition) {
                isAnimating = false
            }

            override fun onTransitionResume(p0: Transition) {}
            override fun onTransitionPause(p0: Transition) {}
        })
    }

    init {

        View.inflate(context, R.layout.twin_tab_navigation_bar, this)

        val res = Res(context)
        Attrs.perform(context, attrs, R.styleable.TwinTabNavigation) {

            leftTabIsSelected = !fetchBoolean(
                R.styleable.TwinTabNavigation_nav2T_rightTabIsSelected, false)

            selectedTabColor = fetchColor(
                R.styleable.TwinTabNavigation_nav2T_selectedTabColor, res.accentColor())

            unselectedTabColor = fetchColor(
                R.styleable.TwinTabNavigation_nav2T_unselectedTabColor, res.accentColor())

            leftTabTitle.text = fetchString(
                R.styleable.TwinTabNavigation_nav2T_leftTabTitle)

            rightTabTitle.text = fetchString(
                R.styleable.TwinTabNavigation_nav2T_rightTabTitle)

            tryFetchResourceID(R.styleable.TwinTabNavigation_nav2T_leftTabIcon) {
                leftTabIcon.setImageResource(it)
            }

            tryFetchResourceID(R.styleable.TwinTabNavigation_nav2T_rightTabIcon) {
                rightTabIcon.setImageResource(it)
            }
        }

        leftTab.setOnClickListener {
            if (!leftTabIsSelected) {
                selectTab(true)
                onTabClickListener(true)
            }
        }

        rightTab.setOnClickListener {
            if (leftTabIsSelected) {
                selectTab(false)
                onTabClickListener(false)
            }
        }

        selectTabInternal(leftTabIsSelected)
    }

    fun setOnTabClickListener(listener: (leftTabWasClicked: Boolean) -> Unit) {
        onTabClickListener = listener
    }

    override fun onInterceptTouchEvent(event: MotionEvent)
            = isAnimating || super.onInterceptTouchEvent(event)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = (context.resources.displayMetrics.density * DESIRED_HEIGHT).toInt()
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == MeasureSpec.UNSPECIFIED ||
            (heightMode == MeasureSpec.AT_MOST && desiredHeight <= heightSize)) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY))
        } else super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun selectTab(left: Boolean) {
        TransitionManager.beginDelayedTransition(this, transition)
        val saved = leftTabIsSelected
        selectTabInternal(left)
        // Update this field after the animation finishes.
        leftTabIsSelected = saved
    }

    private fun selectTabInternal(left: Boolean) {
        if (left) {
            leftTabTitle.visibility = View.VISIBLE
            rightTabTitle.visibility = View.GONE
            leftTabTitle.setTextColor(selectedTabColor)
            rightTabTitle.setTextColor(unselectedTabColor)
            leftTabIcon.tint = selectedTabColor
            rightTabIcon.tint = unselectedTabColor
            removeView(spacer)
            addView(spacer, 0)
            leftTabIsSelected = true
        } else {
            leftTabTitle.visibility = View.GONE
            rightTabTitle.visibility = View.VISIBLE
            leftTabTitle.setTextColor(unselectedTabColor)
            rightTabTitle.setTextColor(selectedTabColor)
            leftTabIcon.tint = unselectedTabColor
            rightTabIcon.tint = selectedTabColor
            removeView(spacer)
            addView(spacer, 2)
            leftTabIsSelected = false
        }
    }
}