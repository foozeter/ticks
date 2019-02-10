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

//        connection = TicksService.makeConnection(this) {
//            onBindingDied { Log.d("mylog", "onBindingDied()") }
//            onDisconnected { Log.d("mylog", "onDisconnected()") }
//            onNullBinding { Log.d("mylog", "onNullBinding()") }
//        }
//
//        connection.connect {
//            Log.d("mylog", "connected!")
//            binder = it
//            it.registerCallback(callback)
//        }
//
        val prevTickTimes = mutableMapOf<Long, Long>()
        val notWorkingTimerIDList = mutableListOf<Long>()
        val workingTimerIDList = mutableListOf<Long>()
        val multiTimer = MultiTimer(1000)

        multiTimer.setCallback(object: TestMultiTimerCallback() {

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
                multiTimer.scrap(timerID)
            }
        })

        button1.setOnClickListener {
            multiTimer.start(20 * 60 * 1000L)
        }

        button2.setOnClickListener {
            if (workingTimerIDList.isNotEmpty()) {
                multiTimer.pause(workingTimerIDList.last())
            }
        }

        button3.setOnClickListener {
            if (notWorkingTimerIDList.isNotEmpty()) {
                multiTimer.resume(notWorkingTimerIDList.last())
            }
        }
    }

//    override fun onStop() {
//        Log.d("mylog", "onStop")
//        super.onStop()
//        binder?.unregisterCallback(callback)
//        connection.disconnect()
//    }

    val listener = object: Quartz.OnOscillateListener {

        var first = true
        var startTime = 0L
        var prevTime = 0L

        override fun onOscillate() {
            val t = SystemClock.elapsedRealtime()
            if (first) {
                prevTime = t
                startTime = t

                first = false
            }
            val d = t - prevTime
            prevTime = t
            Log.d("mylog", "interval = ${d}ms, elapsedTime = ${(t - startTime) / 1000.0}s")
        }
    }

    private val callback = object: TicksService.Callback {

        override fun onTick(timerID: Long, remainingMillis: Long) {
            Log.d("mylog", "left=${remainingMillis}ms ID@$timerID")
        }

        override fun onFinish(timerID: Long) {
            Log.d("mylog", "finish! ID@$timerID")
        }
    }
}
