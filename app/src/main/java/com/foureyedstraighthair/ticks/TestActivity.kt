package com.foureyedstraighthair.ticks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foureyedstraighthair.ticks.jam.Jam
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val jam = Jam()
        jam.setup(decorView)
    }
}
