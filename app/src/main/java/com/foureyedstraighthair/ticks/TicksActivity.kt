package com.foureyedstraighthair.ticks

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_ticks.*

class TicksActivity : AppCompatActivity() {

    private lateinit var bottomSheetContent: HomeBottomSheetContent

    private lateinit var bottomSheetBehavior: BottomSheetBehavior2<*>
    private lateinit var connection: TicksService.Connection

    private var binder: TicksService.LocalService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticks)
        val res = Res(this)

        // Make the navigation bar translucent
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        // Consider the navigation bar height
        val params = newTimerButton.layoutParams as CoordinatorLayout.LayoutParams
        params.bottomMargin += res.navigationBarHeight()

        bottomSheetBehavior = BottomSheetBehavior2.from(bottomSheet).apply {

            // Consider the navigation bar height
            peekHeight += res.navigationBarHeight()

            // Top padding decoration
            val collapsedPaddingTop = bottomSheet.paddingTop.toFloat()
            val expandedPaddingTop = res.statusBarHeight().toFloat()
            val diff = expandedPaddingTop - collapsedPaddingTop
            addDecoration { offset ->
                bottomSheet.apply { setPadding(
                    paddingLeft,
                    (collapsedPaddingTop + diff * offset).toInt(),
                    paddingRight,
                    paddingBottom)
                }
            }

            // Corner radius decoration
            val radius = bottomSheet.cornerRadius
            addDecoration { offset ->
                bottomSheet.cornerRadius = radius * (1f - offset)
            }

            setOnSlideListener { _, offset ->
                bottomSheetContent.onBottomSheetSlide(offset)
            }

            setOnStateChangedListener { _, state ->
                when {
                    isExpanded() -> bottomSheetContent.onBottomSheetExpanded()
                    isCollapsed() -> bottomSheetContent.onBottomSheetCollapsed()
                }
            }
        }

        newTimerButton.setOnClickListener {
            if (bottomSheetBehavior.isExpanded()) {
                bottomSheetContent.onBottomSheetCollapse()
                bottomSheetBehavior.collapse()

                BackgroundColorAnimator()
                    .from(res.colorOf(R.color.home_bottom_sheet_expanded_color))
                    .to(res.colorOf(R.color.home_bottom_sheet_collapsed_color))
                    .targets(appBar, bottomSheet)
                    .duration(300)
                    .start()

                ImageTintAnimator()
                    .from(Color.BLACK)
                    .to(Color.WHITE)
                    .targets(appBar.menuIcon, appBar.optionsIcon)
                    .duration(300)
                    .start()

            } else if (bottomSheetBehavior.isCollapsed()) {
                bottomSheetContent.onBottomSheetExpand()
                bottomSheetBehavior.expand()

                BackgroundColorAnimator()
                    .from(res.colorOf(R.color.home_bottom_sheet_collapsed_color))
                    .to(res.colorOf(R.color.home_bottom_sheet_expanded_color))
                    .targets(appBar, bottomSheet)
                    .duration(300)
                    .start()

                ImageTintAnimator()
                    .from(Color.WHITE)
                    .to(Color.BLACK)
                    .targets(appBar.menuIcon, appBar.optionsIcon)
                    .duration(300)
                    .start()
            }
        }

        bottomSheetContent = HomeBottomSheetContentFragment()
        bottomSheetContent.onConsiderNavigationBarHeight(res.navigationBarHeight())
        bottomSheetContent.onAppBarSet(appBar)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottomSheetContent, bottomSheetContent as Fragment)
            .commit()

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
