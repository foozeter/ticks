package com.foureyedstraighthair.ticks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.foureyedstraighthair.ticks.jam.Jam

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        val jam = Jam.setup(this, R.layout.activity_test)

//        img.pivotX = 0.5f
//        img.pivotY = 0.5f


//        jam.setAnimationCallbackOf(R.id.anim_1, listener)
//        jam.setAnimationCallbackOf(R.id.anim_2, listener)

//        jam.setOnTriggerViewOnClickListenerOf(R.id.button) {
//            img.pivotX = img.width.toFloat() / 2f
//            img.pivotY = img.height.toFloat() / 2f
//        }
//
//        img.setOnClickListener {
//            Log.d("mylog", "getX=${img.x}, getY=${img.y}")
//        }

        val inlime = Jam.setup(this, R.layout.activity_test)
        inlime.setTriggerViewOnLongClickListener(0) {
            Log.d("mylog", "onClick")
        }
    }

}
