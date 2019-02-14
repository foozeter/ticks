package com.foureyedstraighthair.ticks

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

class Attrs(
    context: Context,
    attrs: AttributeSet,
    styleable: IntArray,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0) {

    private var typedArray: TypedArray?
            = context.obtainStyledAttributes(
        attrs, styleable, defStyleAttrs, defStyleRes)

    fun recycle() {
        typedArray?.recycle()
        typedArray = null
    }

    fun fetchResourceID(index: Int, default: Int = 0, onFetch: (id: Int) -> Unit): Int {
        val id = typedArray!!.getResourceId(index, default)
        onFetch(id)
        return id
    }

    fun tryFetchResourceID(index: Int, onFetch: (id: Int) -> Unit) {
        val id = typedArray!!.getResourceId(index, 0)
        if (id != 0) onFetch(id)
    }

    fun fetchString(index: Int, default: String? = null, onFetch: (string: String?) -> Unit = {}): String? {
        val string = typedArray!!.getString(index) ?: default
        onFetch(string)
        return string
    }

    fun tryFetchString(index: Int, onFetch: (string: String) -> Unit) {
        val string = typedArray!!.getString(index)
        if (string != null) onFetch(string)
    }

    fun fetchBoolean(index: Int, default: Boolean = true, onFetch: (boolean: Boolean) -> Unit = {}): Boolean {
        val boolean = typedArray!!.getBoolean(index, default)
        onFetch(boolean)
        return boolean
    }

    fun fetchColor(index: Int, default: Int, onFetch: (color: Int) -> Unit = {}): Int {
        val color = typedArray!!.getColor(index, default)
        onFetch(color)
        return color
    }

    fun tryFetchColor(index: Int, onFetch: (color: Int) -> Unit) {
        val color = typedArray!!.getColor(index, -1)
        if (color != -1) onFetch(color)
    }

    companion object {

        fun perform(
            context: Context,
            attrs: AttributeSet,
            styleable: IntArray,
            defStyleAttrs: Int = 0,
            defStyleRes: Int = 0,
            doSomething: Attrs.() -> Unit) {
            val a = Attrs(context, attrs, styleable, defStyleAttrs, defStyleRes)
            doSomething(a)
            a.recycle()

        }
    }
}