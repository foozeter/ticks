package com.foureyedstraighthair.ticks

import android.os.Handler
import android.os.SystemClock

class Timer(
    private val quartz: Quartz,
    private val millisInFuture: Long): Quartz.OnOscillateListener {
    private val handler = Handler()
    private var id = 0L
    private var startTime = 0L
    private var endTime = 0L
    private var nextTickTime = 0L
    private var leftTime = 0L
    private var isWorking = false
    private var callback: Callback? = null

    init {
        quartz.addOnOscillateListener(this)
    }

    fun scrap() {
        quartz.removeOnOscillateListener(this)
    }

    fun finalize() {
        scrap()
    }

    private fun prepare(timerID: Long) {
        id = timerID
        startTime = rap()
        endTime = startTime + millisInFuture
        nextTickTime = startTime + quartz.period
        leftTime = millisInFuture
    }

    fun start(timerID: Long) {
        if (quartz.isAlreadyActivated) when {

                isWorking -> return

                0 < millisInFuture -> {
                    prepare(timerID)
                    isWorking = true
                    callback?.onTick(id, leftTime)
                }

                else -> {
                    callback?.onFinish(timerID)
                }

        } else throw IllegalStateException(
            "The quartz must be activated at this time.")
    }

    fun stop() {
        isWorking = false
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun removeCallback() {
        callback = null
    }

    override fun onOscillate() {
        if (isWorking) {
            leftTime = endTime - rap()
            if (quartz.period < leftTime) {
                postDelayed(nextTickTime - rap()) {
                    leftTime = endTime - rap()
                    callback?.onTick(id, leftTime)
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

    private fun postDelayed(delay: Long, runnable: () -> Unit) {
        handler.postDelayed(runnable, delay)
    }

    interface Callback {
        fun onTick(timerID: Long, left: Long)
        fun onFinish(timerID: Long)
    }
}