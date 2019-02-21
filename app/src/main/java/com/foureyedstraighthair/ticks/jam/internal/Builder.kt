package com.foureyedstraighthair.ticks.jam.internal

import android.view.View
import android.view.ViewGroup
import com.foureyedstraighthair.ticks.jam.InlineAnimator
import com.foureyedstraighthair.ticks.jam.Jam
import java.util.*

internal class Builder {

    fun createFrom(layout: View): Jam {

        val targets = mutableListOf<View>()
        val triggers = mutableListOf<View>()
        val animators = mutableListOf<InlineAnimator>()

        // Collect InlineAnimators
        layout.scan { child, recycleBin ->
            if (child is InlineAnimator) {
                animators.add(child)
                recycleBin.add(child)
            }
        }

        val targetIDs = animators
            .map { it.target }
            .toMutableList()

        val triggerIDs = animators
            .mapNotNull { it.trigger }
            .toMutableList()

        // Collect targetID and triggerID views
        layout.fullScan {

            if (targetIDs.contains(it.id)) {
                targets.add(it)
                targetIDs.remove(it.id)
            }

            if (triggerIDs.contains(it.id)) {
                triggers.add(it)
                triggerIDs.remove(it.id)
            }

            // Continue until all targetID views and triggerID views is found
            return@fullScan targetIDs.isEmpty() && triggerIDs.isEmpty()
        }

        return Jam(triggers, targets, animators)
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
}