package com.foureyedstraighthair.ticks.jam

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.foureyedstraighthair.ticks.jam.internal.Builder
import com.foureyedstraighthair.ticks.jam.internal.Constant
import com.foureyedstraighthair.ticks.jam.internal.StateHolder
import com.foureyedstraighthair.ticks.jam.internal.TriggerEvent

class Jam internal constructor(
    triggerViews: List<View>,
    targetViews: List<View>,
    animatorViews: List<InlineAnimator>) {

    private val animators = mutableListOf<AnimatorHolder>()
    private val clickObservers = mutableMapOf<Int, OnClickObserver>()
    private val longClickObservers = mutableMapOf<Int, OnLongClickObserver>()

    init {

        val targets = targetViews
            .associateBy { it.id }

        val sharedStates = animatorViews
            .mapNotNull { it.stateName }
            .distinct()
            .associate { it to StateHolder(Constant.DEFAULT_STATE) }

        animatorViews.forEach {
            animators.add(AnimatorHolder(
                it,
                targets[it.target],
                sharedStates[it.stateName]))
        }

        triggerViews.forEach {
            val clickObserver = OnClickObserver()
            val longClickObserver = OnLongClickObserver()
            it.setOnClickListener(clickObserver)
            it.setOnLongClickListener(longClickObserver)
            clickObservers[it.id] = clickObserver
            longClickObservers[it.id] = longClickObserver
        }
    }

    fun setTriggerViewOnClickListener(triggerID: Int, listener: View.OnClickListener) {
        clickObservers[triggerID]?.additionalListener = listener
    }

    fun setTriggerViewOnClickListener(triggerID: Int, listener: (view: View) -> Unit)
            = setTriggerViewOnClickListener(triggerID, onClickListener(listener))

    fun removeTriggerViewOnClickListener(triggerID: Int) {
        clickObservers[triggerID]?.additionalListener = null
    }

    fun setTriggerViewOnLongClickListener(triggerID: Int, listener: View.OnLongClickListener) {
        longClickObservers[triggerID]?.additionalListener = listener
    }

    fun setTriggerViewOnLongClickListener(triggerID: Int, listener: (view: View) -> Unit)
            = setTriggerViewOnLongClickListener(triggerID, onLongClickListener(listener))

    fun removeTriggerViewOnLongClickListener(triggerID: Int) {
        longClickObservers[triggerID]?.additionalListener = null
    }

    fun addAnimatorListener(animatorId: Int, listener: AnimatorHolderListener) {
        animators.find { it.id == animatorId }?.addListener(listener)
    }

    fun removeAnimatorListener(animatorId: Int, listener: AnimatorHolderListener) {
        animators.find { it.id == animatorId }?.removeListener(listener)
    }

    fun findAnimator(animatorId: Int) = animators.find { it.id == animatorId }

    private fun onClickListener(listener: (view: View) -> Unit)
            = object: View.OnClickListener {
        override fun onClick(view: View) = listener(view)
    }

    private fun onLongClickListener(listener: (view: View) -> Unit)
            = object: View.OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            listener(view)
            return true
        }
    }

    private inner class OnClickObserver: View.OnClickListener {

        var additionalListener: View.OnClickListener? = null

        override fun onClick(view: View) {
            additionalListener?.onClick(view)
            animators.forEach { it.fire(view, TriggerEvent.ON_CLICK) }
        }
    }

    private inner class OnLongClickObserver: View.OnLongClickListener {

        var additionalListener: View.OnLongClickListener? = null

        override fun onLongClick(view: View): Boolean {
            additionalListener?.onLongClick(view)
            animators.forEach { it.fire(view, TriggerEvent.ON_LONG_CLICK) }
            return true
        }
    }

    companion object {

        fun setup(layout: View)
                = Builder().createFrom(layout)

        fun setup(activity: AppCompatActivity, layout: Int): Jam {
            activity.setContentView(layout)
            val rootView = activity
                .findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0)
            return setup(rootView)
        }
    }
}