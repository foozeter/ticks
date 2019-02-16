package com.foureyedstraighthair.ticks

import android.view.View

class BackgroundColorAnimator: ColorAnimator<View>() {
    override fun onUpdate(target: View, animatedColor: Int) {
        target.setBackgroundColor(animatedColor)
    }
}