package com.foureyedstraighthair.ticks.jam.anim

import android.animation.Animator
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import com.foureyedstraighthair.ticks.jam.InlineAnimationCallback
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineAnim

abstract class Anim (
    val jam: Jam,
    definition: InlineAnim
)
    : Animator.AnimatorPauseListener,
    Animator.AnimatorListener {

    private val tag = Anim::class.java.name

    @IdRes val id = definition.id
    @IdRes val targetID = definition.target
    @IdRes val triggerID = definition.trigger
    val duration = Math.max(0, definition.duration)
    val startDelay = Math.max(0, definition.startDelay)
    val interpolator = definition.interpolator
    val targetStateBefore = definition.targetStateBefore
    val targetStateAfter = definition.targetStateAfter
    val triggerEventFlags = definition.triggerEventFlags
    val allowInterruption = definition.allowInterruption

    var callback: InlineAnimationCallback? = null

    private var animator: Animator? = null

    fun startIfConditionMatch(trigger: View, eventFlag: Int, jam: Jam) {
        val target = jam.findTarget(targetID)
        val flag = jam.findTargetState(targetID)
        if (trigger.id == triggerID &&
            triggerEventFlags and eventFlag == eventFlag &&
            target != null &&
            flag != null &&
            flag == targetStateBefore) {
            start(target)
        } else if (target == null) {
            Log.w(tag, "unknown target id:$targetID")
        }
    }

    private fun start(target: View) {
        if (allowInterruption ||
            animator == null ||
            !animator!!.isStarted) {
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
    }

    fun cancel() {
        animator?.cancel()
    }

    protected abstract fun onCreateAnimator(target: View): Animator

    override fun onAnimationRepeat(animation: Animator) {
        callback?.onAnimationRepeat(this)
    }

    override fun onAnimationEnd(animation: Animator) {
        jam.setTargetState(targetID, targetStateAfter)
        callback?.onAnimationEnd(this)
    }

    override fun onAnimationCancel(animation: Animator) {
        callback?.onAnimationCancel(this)
    }

    override fun onAnimationStart(animation: Animator) {
        callback?.onAnimationStart(this)
    }

    override fun onAnimationPause(animation: Animator?) {
        callback?.onAnimationPause(this)
    }

    override fun onAnimationResume(animation: Animator?) {
        callback?.onAnimationResume(this)
    }
}