package com.foureyedstraighthair.ticks

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_ticks.*

class TicksActivity : AppCompatActivity() {

    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    private lateinit var connection: TicksService.Connection
    private var sheetCornerRadius = 0f
    private var binder: TicksService.LocalService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticks)

        connection = TicksService.makeConnection(this) {
            onBindingDied { Log.d("mylog", "onBindingDied()") }
            onDisconnected { Log.d("mylog", "onDisconnected()") }
            onNullBinding { Log.d("mylog", "onNullBinding()") }
        }

        sheetCornerRadius = resources.getDimensionPixelSize(R.dimen.home_bottom_sheet_corner_radius).toFloat()
        sheetBehavior = BottomSheetBehavior.from(findViewById<View>(R.id.bottom_sheet))
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        root_layout.post { sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED }
        sheetBehavior.setBottomSheetCallback(bottomSheetCallback)

//        supportFragmentManager.beginTransaction().replace(R.id.container, NewTimerFragment()).commit()
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

    private val bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(sheet: View, slideOffset: Float) {
            if (0f <= slideOffset) updateCornerRadius(sheet, slideOffset)
        }

        override fun onStateChanged(sheet: View, state: Int) {
            when (state) {
                BottomSheetBehavior.STATE_EXPANDED -> updateCornerRadius(sheet, 1f)
                BottomSheetBehavior.STATE_COLLAPSED -> updateCornerRadius(sheet, 0f)
            }
        }

        private fun updateCornerRadius(sheet: View, fraction: Float) {
            (sheet as BumpyView).cornerRadius = sheetCornerRadius * (2 * fraction - 1)
        }
    }
}
