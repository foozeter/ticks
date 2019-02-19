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
        setContentView(R.layout.activity_test)

        img.setOnClickListener {
            Log.d("mylog", "on click")
        }

        img.translationX += 100

        val listener = object: InlineAnimationCallback() {
            override fun onAnimationStart(animation: Anim) {
                Log.d("mylog", "onStart : (left, top) = (${img.left}, ${img.top})")
            }

            override fun onAnimationEnd(animation: Anim) {
                Log.d("mylog", "onEnd : (left, top) = (${img.left}, ${img.top})")
            }
        }

        val jam = Jam()
        jam.setup(decorView)
//        jam.setAnimationCallbackOf(R.id.anim_1, listener)
//        jam.setAnimationCallbackOf(R.id.anim_2, listener)

        img.setOnClickListener {
            Log.d("mylog", "onClick img")
        }
    }

}
