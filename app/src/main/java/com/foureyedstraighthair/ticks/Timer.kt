package com.foureyedstraighthair.ticks

import android.os.Handler
import android.os.SystemClock

class Timer(
    private val quartz: Quartz,
    private val millisInFuture: Long): Quartz.OnOscillateListener {
    private val handler = Handler()
    private var startTime = 0L
    private var endTime = 0L
    private var nextTickTime = 0L
    private var leftTime = 0L
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

    private fun prepare() {
        if (!started) leftTime = millisInFuture
        startTime = rap()
        endTime = startTime + leftTime
        nextTickTime = startTime + quartz.period
    }

    fun start() {
        if (quartz.isAlreadyActivated) when {

                started -> return

                0 < millisInFuture -> {
                    prepare()
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
            isWorking = false
            updateLeftTime()
            callback?.onPause(id)
        }
    }

    fun resume() {
        if (started && !isWorking) {
            prepare()
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
            updateLeftTime()
            if (quartz.period < leftTime) {
                postDelayed(nextTickTime - rap()) {
                    // If the timer is paused at this time,
                    // this post will be ignored.
                    if (isWorking) {
                        updateLeftTime()
                        callback?.onTick(id, leftTime)
                    }
                }
                nextTickTime += quartz.period
            } else {
                stop()
                postDelayed(leftTime) {
                    callback?.onFinish(id)
                }
            }
        }
    }

    private fun rap() = SystemClock.elapsedRealtime()

    private fun updateLeftTime() {
        leftTime = endTime - rap()
    }

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