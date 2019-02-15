package com.foureyedstraighthair.ticks

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_ticks.*

class TicksActivity : AppCompatActivity() {

    private lateinit var bottomSheetContentsEnterAnimation: Animation
    private lateinit var bottomSheetContentsExitAnimation: Animation

    private lateinit var bottomSheetBehavior: UserLockBottomSheetBehavior<*>
    private lateinit var connection: TicksService.Connection
    private var binder: TicksService.LocalService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticks)

        bottomSheetContentsEnterAnimation = AnimationUtils.loadAnimation(
            this, R.anim.main_bottom_sheet_contents_enter).apply {
            setAnimationListener(object: Animation.AnimationListener {

                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    mainMenu.visibility = View.VISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {}
            })
        }

        bottomSheetContentsExitAnimation = AnimationUtils.loadAnimation(
            this, R.anim.main_bottom_sheet_contents_exit).apply {
            setAnimationListener(object: Animation.AnimationListener {

                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    mainMenu.visibility = View.INVISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {}
            })
        }

        val res = Res(this)

        // Make the navigation bar translucent
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        // Consider the navigation bar height
        bottomSheetBehavior = UserLockBottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight += res.navigationBarHeight()
        val params = newTimerButton.layoutParams as CoordinatorLayout.LayoutParams
        params.bottomMargin += res.navigationBarHeight()
        mainMenu.findViewById<ViewGroup>(R.id.itemContainer).apply {
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + res.navigationBarHeight())
        }

        bottomSheetBehavior.apply {

            val sheetCornerRadius = res.pxOf(R.dimen.home_bottom_sheet_corner_radius).toFloat()
            val collapsedSheetColor = res.colorOf(R.color.home_bottom_sheet_collapsed_color)
            val expandedSheetColor = res.colorOf(R.color.home_bottom_sheet_expanded_color)
            val argbEvaluator = ArgbEvaluator()

            setOnSlideListener { _, offset ->
                bottomSheet.cornerRadius = sheetCornerRadius * (offset - 1f)
                if (bottomSheetBehavior.isRecentlyExpanded()) {
                    bottomSheet.setBackgroundColor(argbEvaluator.evaluate(
                        offset, collapsedSheetColor, expandedSheetColor) as Int)
                } else if (bottomSheetBehavior.isRecentlyCollapsed()) {
                    bottomSheet.setBackgroundColor(argbEvaluator.evaluate(
                        offset, collapsedSheetColor, expandedSheetColor) as Int)
                }
            }

            setOnStateChangedListener { _, _ ->
                when {

                    isExpanded() -> {
                        bottomSheet.cornerRadius = 0f
                        bottomSheet.setBackgroundColor(expandedSheetColor)
                    }

                    isCollapsed() -> {
                        bottomSheet.cornerRadius = -sheetCornerRadius
                        bottomSheet.setBackgroundColor(collapsedSheetColor)
                    }
                }
            }
        }

        newTimerButton.setOnClickListener {
            if (bottomSheetBehavior.isExpanded()) {
                bottomSheetBehavior.collapse()
                mainMenu.startAnimation(bottomSheetContentsExitAnimation)
            } else if (bottomSheetBehavior.isCollapsed()) {
                bottomSheetBehavior.expand()
                mainMenu.startAnimation(bottomSheetContentsEnterAnimation)
            }
        }

        // Drawing svg is heavy process, so I'll draw it from the first.
        mainMenu.post {
            mainMenu.visibility = View.INVISIBLE
        }

        connection = TicksService.makeConnection(this) {
            onBindingDied { Log.d("mylog", "onBindingDied()") }
            onDisconnected { Log.d("mylog", "onDisconnected()") }
            onNullBinding { Log.d("mylog", "onNullBinding()") }
        }
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
