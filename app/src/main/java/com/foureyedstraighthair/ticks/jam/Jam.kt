package com.foureyedstraighthair.ticks.jam

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.anim.Anim
import com.foureyedstraighthair.ticks.jam.anim.ColorPropertyAnim
import com.foureyedstraighthair.ticks.jam.anim.FloatPropertyAnim
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineAnim
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineColorPropertyAnim
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineFloatPropertyAnim
import java.lang.ref.WeakReference
import java.util.*

class Jam private constructor(layout: View) {

    private val tag = Jam::class.java.name

    private val targets: Map<Int, WeakReference<View>>
    private val triggers: Map<Int, WeakReference<View>>
    private val onClickObservers: Map<Int, OnClickObserver>
    private val onLongClickObservers: Map<Int, OnLongClickObserver>
    private val animations: List<Anim>
    private val targetFlags = mutableMapOf<Int, Int>()

    init {

        val targets = mutableMapOf<Int, WeakReference<View>>()
        val triggers = mutableMapOf<Int, WeakReference<View>>()
        val onClickObservers = mutableMapOf<Int, OnClickObserver>()
        val onLongClickObservers = mutableMapOf<Int, OnLongClickObserver>()
        val animations = mutableListOf<Anim>()

        // Collect children of InlineAnim
        layout.scan { child, recycleBin ->
            when (child) {
                is InlineAnim -> {
                    animations.add(makeAnimFrom(child))
                    recycleBin.add(child)
                }
            }
        }

        val targetIDs = animations
            .asSequence()
            .map { it.targetID }
            .filter { it > 0 }
            .toMutableList()

        val triggerIDs = animations
            .asSequence()
            .map { it.triggerID }
            .filter { it > 0 }
            .toMutableList()

        // Collect target and trigger views
        layout.fullScan {

            if (targetIDs.contains(it.id)) {
                targets[it.id] = WeakReference(it)
                targetIDs.remove(it.id)
            }

            if (triggerIDs.contains(it.id)) {
                val onClickObserver = OnClickObserver()
                val onLongClickObserver = OnLongClickObserver()
                it.setOnClickListener(onClickObserver)
                it.setOnLongClickListener(onLongClickObserver)
                onClickObservers[it.id] = onClickObserver
                onLongClickObservers[it.id] = onLongClickObserver
                triggers[it.id] = WeakReference(it)
                triggerIDs.remove(it.id)
            }

            // Continue until all target views and trigger views is found
            return@fullScan targetIDs.isEmpty() && triggerIDs.isEmpty()
        }

        // Set target state flags as a default value.
        targets.keys.forEach {
            targetFlags[it] = Default.TARGET_FLAG
        }

        this.targets = targets
        this.triggers = triggers
        this.onClickObservers = onClickObservers
        this.onLongClickObservers = onLongClickObservers
        this.animations = animations
    }

    fun findTarget(id: Int) = targets[id]?.get()

    fun findTrigger(id: Int) = triggers[id]?.get()

    fun findTargetFlag(id: Int) = targetFlags[id]

    fun setTargetFlag(id: Int, flag: Int) {
        targetFlags[id] = flag
    }

    fun setOnTriggerViewOnClickListenerOf(triggerID: Int, listener: (view: View) -> Unit) {
        onClickObservers[triggerID]?.additionalListener = listener
    }

    fun removeOnTriggerViewOnClickListenerOf(triggerID: Int) {
        onClickObservers[triggerID]?.additionalListener = {}
    }

    fun setOnTriggerViewLongClickListenerOf(triggerID: Int, listener: (view: View) -> Unit) {
        onLongClickObservers[triggerID]?.additionalListener = listener
    }

    fun removeOnTriggerViewLongClickListenerOf(triggerID: Int) {
        onLongClickObservers[triggerID]?.additionalListener = {}
    }

    fun setAnimationCallbackOf(animationID: Int, callback: InlineAnimationCallback) {
        animations.find { it.id == animationID }?.callback = callback
    }

    fun removeAnimationCallbackOf(animationID: Int) {
        animations.find { it.id == animationID }?.callback = null
    }

    private fun onEventOccurred(trigger: View, eventFlag: Int) {
        animations.forEach { it.startIfConditionMatch(trigger, eventFlag, this) }
    }

    private fun makeAnimFrom(inlineAnim: InlineAnim)
            = when (inlineAnim) {
        is InlineColorPropertyAnim -> ColorPropertyAnim(this, inlineAnim)
        is InlineFloatPropertyAnim -> FloatPropertyAnim(this, inlineAnim)
        else -> throw IllegalArgumentException("$tag : Unknown inline animation object.")
    }

    private fun View.scan(onChild: (child: View, recycleBin: MutableList<View>) -> Unit) {
        if (this !is ViewGroup) return
        val recycleBin = mutableListOf<View>()
        for (i in 0 until childCount) {
            onChild(getChildAt(i), recycleBin)
        }
        recycleBin.forEach { removeView(it) }
    }

    private fun View.fullScan(onChild: (view: View) -> Boolean) {
        // Breadth first search
        val queue = ArrayDeque<View>()
        queue.add(this)
        while (queue.isNotEmpty()) {
            val child = queue.poll()
            val finish = onChild(child)
            if (finish) return
            if (child is ViewGroup) child.addChildrenInto(queue)
        }
    }

    private fun ViewGroup.addChildrenInto(queue: ArrayDeque<View>) {
        for (i in 0 until childCount) queue.add(getChildAt(i))
    }

    private inner class OnClickObserver: View.OnClickListener {

        private val onClickFlag = R.integer.flag_triggerEvent_onClick

        var additionalListener = { _: View -> }

        override fun onClick(view: View) {
            Log.d("mylog", "onClick")
            onEventOccurred(view, view.resources.getInteger(onClickFlag))
            additionalListener(view)
        }
    }

    private inner class OnLongClickObserver: View.OnLongClickListener {

        private val onLongClickFlag = R.integer.flag_triggerEvent_onLongClick

        var additionalListener = { _: View -> }

        override fun onLongClick(view: View): Boolean {
            Log.d("mylog", "onLongClick")
            onEventOccurred(view, view.resources.getInteger(onLongClickFlag))
            additionalListener(view)
            return true
        }
    }

    companion object {

        fun setup(layout: View) = Jam(layout)
    }
}