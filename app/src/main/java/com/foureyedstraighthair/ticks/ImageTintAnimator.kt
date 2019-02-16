package com.foureyedstraighthair.ticks

import android.widget.ImageView

class ImageTintAnimator: ColorAnimator<ImageView>() {
    override fun onUpdate(target: ImageView, animatedColor: Int) {
        target.setColorFilter(animatedColor)
    }
}