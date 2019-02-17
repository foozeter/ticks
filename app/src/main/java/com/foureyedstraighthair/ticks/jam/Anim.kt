package com.foureyedstraighthair.ticks.jam

import android.animation.Animator
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import com.foureyedstraighthair.ticks.jam.constant.TriggerEvent

abstract class Anim (
    val jam: Jam,
    definition: InlineAnim)
    : Animator.AnimatorPauseListener,
    Animator.AnimatorListener {

    private val tag = Anim::class.java.name

    @IdRes val id = definition.id
    @IdRes val targetID = definition.target
    @IdRes val triggerID = definition.trigger
    val duration = Math.max(0, definition.duration)
    val startDelay = Math.max(0, definition.startDelay)
    val interpolator = definition.interpolator.generate()
    val targetFlagBefore = definition.targetFlagBefore
    val targetFlagAfter = definition.targetFlagAfter
    val triggerEvent = definition.triggerEvent

    var callback: InlineAnimationCallback? = null

    private var animator: Animator? = null

    fun startIfConditionMatch(trigger: View, event: TriggerEvent, jam: Jam) {
        val target = jam.findTarget(targetID)
        val flag = jam.findTargetFlag(targetID)

        if (trigger.id == triggerID &&
            event == triggerEvent &&
            target != null &&
            flag != null &&
            flag == targetFlagBefore) {
            start(target)
        } else if (target == null) {
            Log.w(tag, "unknown target id:$targetID")
        }
    }

    fun start(target: View) {
        cancel()
        animator = onCreateAnimator(target).apply {
            duration = this@Anim.duration
            interpolator = this@Anim.interpolator
            startDelay = this@Anim.startDelay
            addListener(this@Anim)
            addPauseListener(this@Anim)
            start()
        }
    }

    fun cancel() {
        animator?.cancel()
    }

    protected abstract fun onCreateAnimator(target: View): Animator

    override fun onAnimationRepeat(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
        jam.setTargetFlag(targetID, targetFlagAfter)
    }

    override fun onAnimationCancel(animation: Animator) {

    }

    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationPause(animation: Animator?) {
    }

    override fun onAnimationResume(animation: Animator?) {
    }
}