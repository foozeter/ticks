package com.foureyedstraighthair.ticks

import android.os.*
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
//        timer_start.setOnClickListener {
//            binder?.startTimer(10000, 1000)
//        }

//        quartz.addOnOscillateListener(listener)
        quartz.activate()

        var baseID = 0L
        timer_start.setOnClickListener {
            val timer = Timer(quartz)
            timer.id = ++baseID
            timer.setCallback(object: Timer.Callback {

                override fun onPause(timerID: Long) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResume(timerID: Long) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onCancelled(timerID: Long) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStart(timerID: Long) {

                }

                override fun onTick(timerID: Long, left: Long) {
                    Log.d("mylog", "left=${left}ms ID@$timerID")
                }

                override fun onFinish(timerID: Long) {
                    Log.d("mylog", "finish ID@$timerID")
                }
            })
            timer.start(10000)
        }

        val timer = Timer(quartz)
        timer.setCallback(TestTimerCallback())
        button2.setOnClickListener {
            if (timer.started) {
                if (timer.isWorking) timer.pause()
                else timer.resume()
            } else {
                timer.start(10000)
            }
        }
    }

    val quartz = Quartz()

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

    override fun onStop() {
        super.onStop()
        quartz.quit()
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
