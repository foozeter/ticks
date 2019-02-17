package com.foureyedstraighthair.ticks.jam

abstract class InlineAnimationCallback {
    open fun onAnimationRepeat(animation: Anim) {}
    open fun onAnimationCancel(animation: Anim) {}
    open fun onAnimationEnd(animation: Anim) {}
    open fun onAnimationStart(animation: Anim) {}
    open fun onAnimationPause(animation: Anim) {}
    open fun onAnimationResume(animation: Anim) {}
}