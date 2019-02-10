package com.foureyedstraighthair.ticks

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import java.lang.ref.WeakReference

class TicksService: Service() {

    @Volatile private lateinit var countDownLooper: Looper
    private val binder = LocalService()
    private var boundClientsCount = 0

    companion object {

        private const val SHUTDOWN_DELAY = 50000L

        fun makeConnection(context: Context)
                = makeConnection(context) {}

        fun makeConnection(context: Context, init: Connection.() -> Unit)
                = Connection(context).apply(init)
    }

    class Connection(private val context: Context) {

        private var onConnectedListener = {_: LocalService -> }
        private var onDisconnectedListener = {}
        private var onBindingDiedListener = {}
        private var onNullBindingListener = {}
        var isConnected = false; private set
        var binder: LocalService? = null; private set

        private val connectionListener = object: ServiceConnection {

            override fun onServiceDisconnected(name: ComponentName) {
                isConnected = false
                binder = null
                onDisconnectedListener()
            }

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                isConnected = true
                binder = service as LocalService
                onConnectedListener(service)
            }

            override fun onBindingDied(name: ComponentName?) {
                isConnected = false
                binder = null
                onBindingDiedListener()
            }

            override fun onNullBinding(name: ComponentName?) {
                isConnected = true
                binder = null
                onNullBindingListener()
            }
        }

        fun connect(onConnected: (binder: LocalService) -> Unit) {
            onConnectedListener = onConnected
            connect()
        }

        fun connect() {
            if (isConnected) {
                val binder = binder
                if (binder != null) onConnectedListener(binder)
            } else {
                val intent = Intent(context, TicksService::class.java)
                // Start a service to prevent it from being killed by the system.
                context.startService(intent)
                context.bindService(intent, connectionListener, Context.BIND_AUTO_CREATE)
            }
        }

        fun disconnect() {
            if (isConnected) context.unbindService(connectionListener)
        }

        fun onConnected(listener: (binder: LocalService) -> Unit) {
            onConnectedListener = listener
        }

        fun onDisconnected(listener: () -> Unit) {
            onDisconnectedListener = listener
        }

        fun onBindingDied(listener: () -> Unit) {
            onBindingDiedListener = listener
        }

        fun onNullBinding(listener: () -> Unit) {
            onNullBindingListener = listener
        }
    }

    inner class LocalService: Binder() {

        fun registerCallback(callback: Callback) {
//            callbacks.add(callback)
        }

        fun unregisterCallback(callback: Callback) {
//            callbacks.remove(callback)
        }

        fun startTimer(millisInFuture: Long, interval: Long)
                = onStartTimer(millisInFuture, interval)

        fun resumeTimer(timerID: Long)
                = onResumeTimer(timerID)

        fun pauseTimer(timerID: Long)
                = onPauseTimer(timerID)

        fun stopTimer(timerID: Long)
                = onStopTimer(timerID)
    }

    interface Callback {
        fun onTick(timerID: Long, remainingMillis: Long)
        fun onFinish(timerID: Long)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("mylog", "onStartCommand()")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("mylog", "onBind()")
        ++boundClientsCount
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d("mylog", "onUnbind()")
        --boundClientsCount
        if (boundClientsCount == 0)
            Handler().postDelayed({ tryShutdown() }, SHUTDOWN_DELAY)
        return false
    }

    override fun onCreate() {
        Log.d("mylog", "onCreate()")
        super.onCreate()
        val workerThread = HandlerThread(TicksService::class.java.name)
        workerThread.start()
        countDownLooper = workerThread.looper
    }

    override fun onDestroy() {
        Log.d("mylog", "onDestroy()")
        super.onDestroy()
        countDownLooper.quit()
    }

    private fun onStartTimer(millisInFuture: Long, interval: Long): Long {
        return 0
    }

    private fun onResumeTimer(timerID: Long) {

    }

    private fun onPauseTimer(timerID: Long) {

    }

    private fun onStopTimer(timerID: Long) {
    }

    private fun tryShutdown() {
//        if (timers.isEmpty() &&
//            boundClientsCount == 0) {
//            stopSelf()
//        }
    }
}