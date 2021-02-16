package am.timerlabs.animation

import android.view.View

interface AnimateViewInterface {
    fun animateViewHeight(view: View, durationMilliseconds: Long, from: Int, to: Int)
    fun animateViewOpacity(view: View, durationMilliseconds: Long, from: Float, value: Float)
    fun animateViewOpacity(view: View, durationMilliseconds: Long, from: Float, value: Float, onAnimEnd: () -> Unit)
    fun cancel()
}