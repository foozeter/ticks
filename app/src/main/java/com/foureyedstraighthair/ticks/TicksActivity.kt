package com.foureyedstraighthair.ticks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

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

        supportFragmentManager.beginTransaction().replace(R.id.container, CreateTimerFragment()).commit()
    }

//    override fun onStart() {
//        super.onStart()
//        connection.connect {
//            Log.d("mylog", "connected!")
//            binder = it
//            it.registerCallback(TestMultiTimerCallback())
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        binder?.unregisterCallback(callback)
//        connection.disconnect()
//    }
}
