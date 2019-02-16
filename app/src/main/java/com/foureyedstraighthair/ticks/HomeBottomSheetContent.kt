package com.foureyedstraighthair.ticks

interface HomeBottomSheetContent {
    fun onBottomSheetExpand()
    fun onBottomSheetCollapse()
    fun onBottomSheetSlide(offset: Float)
    fun onBottomSheetExpanded()
    fun onBottomSheetCollapsed()
    fun onConsiderNavigationBarHeight(height: Int)
    fun onAppBarSet(appbar: AppBar)
}