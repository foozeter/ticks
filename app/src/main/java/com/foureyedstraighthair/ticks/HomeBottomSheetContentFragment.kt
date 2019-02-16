package com.foureyedstraighthair.ticks

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class HomeBottomSheetContentFragment
    : Fragment(), HomeBottomSheetContent {

    private lateinit var decorView: View
    private lateinit var mainMenu: NestedScrollView
    private lateinit var mainMenuItemContainer: ViewGroup
    private lateinit var appBar: AppBar

    private lateinit var expandingAnimation: Animation
    private lateinit var collapsingAnimation: Animation

    private var doAfterViewCreated = mutableListOf<() -> Unit>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        decorView = inflateView(inflater, container)
        mainMenu = decorView.findViewById(R.id.mainMenu)
        mainMenuItemContainer = mainMenu.findViewById(R.id.itemContainer)
        doAfterViewCreated.forEach { it.invoke() }

        return decorView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        expandingAnimation = AnimationUtils.loadAnimation(
            context, R.anim.main_bottom_sheet_contents_enter).apply {
            setAnimationListener(
                onAnimationEnd {
                    decorView.visibility = View.VISIBLE
                })
        }

        collapsingAnimation = AnimationUtils.loadAnimation(
            context, R.anim.main_bottom_sheet_contents_exit).apply {
            setAnimationListener(
                onAnimationEnd {
                decorView.visibility = View.INVISIBLE
                })
        }
    }

    override fun onBottomSheetExpand() {
        decorView.startAnimation(expandingAnimation)
    }

    override fun onBottomSheetCollapse() {
        decorView.startAnimation(collapsingAnimation)
        // Reset the scrolling position
        mainMenu.smoothScrollTo(0, 0)
    }

    override fun onBottomSheetSlide(offset: Float) {
    }

    override fun onBottomSheetExpanded() {
    }

    override fun onBottomSheetCollapsed() {
    }

    override fun onAppBarSet(appbar: AppBar) {
        doAfterViewCreated {
            this.appBar = appbar
            appbar.makeScrollViewElevationRelationShip(mainMenu)
        }
    }

    override fun onConsiderNavigationBarHeight(height: Int) =
        doAfterViewCreated {
            mainMenuItemContainer.apply {
                setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + height)
            }
        }

    /**
     * HELPER METHODS
     */

    private fun doAfterViewCreated(task: () -> Unit) {
        doAfterViewCreated.add(task)
    }

    private fun inflateView(inflater: LayoutInflater, container: ViewGroup?)
            = inflater.inflate(R.layout.fragment_home_bottom_sheet_content, container, false)

    private fun onAnimationEnd(listener: () -> Unit)
            = object: Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) = listener()
    }
}