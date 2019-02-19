package com.foureyedstraighthair.ticks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.foureyedstraighthair.ticks.jam.InlineAnimationCallback
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.anim.Anim
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jam = Jam.setup(this, R.layout.activity_test)

//        img.pivotX = 0.5f
//        img.pivotY = 0.5f

        val listener = object: InlineAnimationCallback() {
            override fun onAnimationStart(animation: Anim) {
                Log.d("mylog", "onStart : (left, top) = (${img.left}, ${img.top})")
            }

            override fun onAnimationEnd(animation: Anim) {
                Log.d("mylog", "onEnd : (left, top) = (${img.left}, ${img.top})")
            }
        }

//        jam.setAnimationCallbackOf(R.id.anim_1, listener)
//        jam.setAnimationCallbackOf(R.id.anim_2, listener)

        jam.setOnTriggerViewOnClickListenerOf(R.id.button) {
            img.pivotX = img.width.toFloat() / 2f
            img.pivotY = img.height.toFloat() / 2f
        }

        img.setOnClickListener {
            Log.d("mylog", "getX=${img.x}, getY=${img.y}")
        }
    }

}
