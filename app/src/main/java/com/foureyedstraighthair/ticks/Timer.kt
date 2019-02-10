package com.foureyedstraighthair.ticks

import android.os.Handler
import android.os.SystemClock

class Timer(
    private val quartz: Quartz): Quartz.OnOscillateListener {
    private val handler = Handler()
    private var startTime = 0L
    private var endTime = 0L
    private var nextTickTime = 0L
    private var savedLeftTime = 0L
    private var callback: Callback? = null

    var started = false; private set
    var isWorking = false; private set
    var id = 0L

    init {
        quartz.addOnOscillateListener(this)
    }

    fun scrap() {
        stop()
        quartz.removeOnOscillateListener(this)
    }

    fun finalize() {
        scrap()
    }

    private fun prepare(millisInFuture: Long) {
        startTime = rap()
        endTime = startTime + millisInFuture
        nextTickTime = startTime + quartz.period
    }

    fun start(millisInFuture: Long) {
        if (quartz.isAlreadyActivated) when {

                started -> return

                0 < millisInFuture -> {
                    prepare(millisInFuture)
                    started = true
                    isWorking = true
                    callback?.onStart(id)
                }

                else -> {
                    callback?.onFinish(id)
                }

        } else throw IllegalStateException(
            "The quartz must be activated at this time.")
    }

    fun pause() {
        if (started && isWorking) {
            savedLeftTime = rapLeft()
            isWorking = false
            callback?.onPause(id)
        }
    }

    fun resume() {
        if (started && !isWorking) {
            prepare(savedLeftTime)
            isWorking = true
            callback?.onResume(id)
        }
    }

    fun cancel() {
        if (started) {
            stop()
            callback?.onCancelled(id)
        }
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun removeCallback() {
        callback = null
    }

    override fun onOscillate() {
        if (isWorking) {
            val left = rapLeft()
            if (quartz.period < left) {
                postDelayed(nextTickTime - rap()) {
                    // If the timer is paused at this time,
                    // this post will be ignored.
                    if (isWorking) callback?.onTick(id, rapLeft())
                }
                nextTickTime += quartz.period
            } else {
                stop()
                postDelayed(left) {
                    callback?.onFinish(id)
                }
            }
        }
    }

    private fun rap() = SystemClock.elapsedRealtime()

    private fun rapLeft() = endTime - rap()

    private fun stop() {
        started = false
        isWorking = false
    }

    private fun postDelayed(delay: Long, runnable: () -> Unit) {
        handler.postDelayed(runnable, delay)
    }

    interface Callback {
        fun onStart(timerID: Long)
        fun onPause(timerID: Long)
        fun onResume(timerID: Long)
        fun onCancelled(timerID: Long)
        fun onFinish(timerID: Long)
        fun onTick(timerID: Long, left: Long)
    }
}