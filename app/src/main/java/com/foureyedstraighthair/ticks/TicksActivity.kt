package com.foureyedstraighthair.ticks

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_ticks.*

class TicksActivity : AppCompatActivity() {

    private lateinit var connection: TicksService.Connection
    private var binder: TicksService.LocalService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticks)

        connection = TicksService.makeConnection(this) {
            onBindingDied { Log.d("mylog", "onBindingDied()") }
            onDisconnected { Log.d("mylog", "onDisconnected()") }
            onNullBinding { Log.d("mylog", "onNullBinding()") }
        }

        button1.setOnClickListener {
            binder?.startTimer(20 * 60 * 1000L)
        }

        button2.setOnClickListener {
            if (workingTimerIDList.isNotEmpty()) {
                binder?.pauseTimer(workingTimerIDList.last())
            }
        }

        button3.setOnClickListener {
            if (notWorkingTimerIDList.isNotEmpty()) {
                binder?.resumeTimer(notWorkingTimerIDList.last())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        connection.connect {
            Log.d("mylog", "connected!")
            binder = it
            it.registerCallback(TestMultiTimerCallback())
        }
    }

    override fun onStop() {
        super.onStop()
        binder?.unregisterCallback(callback)
        connection.disconnect()
    }

    val prevTickTimes = mutableMapOf<Long, Long>()
    val notWorkingTimerIDList = mutableListOf<Long>()
    val workingTimerIDList = mutableListOf<Long>()

    private val callback = object: TestMultiTimerCallback() {

        override fun onStart(timerID: Long) {
            super.onStart(timerID)
            prevTickTimes[timerID] = SystemClock.elapsedRealtime()
            workingTimerIDList.add(timerID)
        }

        override fun onPause(timerID: Long) {
            super.onPause(timerID)
            workingTimerIDList.remove(timerID)
            notWorkingTimerIDList.add(timerID)
        }

        override fun onResume(timerID: Long) {
            super.onResume(timerID)
            notWorkingTimerIDList.remove(timerID)
            workingTimerIDList.add(timerID)
        }

        override fun onFinish(timerID: Long) {
            super.onFinish(timerID)
            notWorkingTimerIDList.remove(timerID)
            workingTimerIDList.remove(timerID)
            prevTickTimes.remove(timerID)
            binder?.scrapTimer(timerID)
        }
    }
}
