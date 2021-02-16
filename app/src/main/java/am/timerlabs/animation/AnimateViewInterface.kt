package am.timerlabs.animation

import android.view.View

interface AnimateViewInterface {
    fun animateViewHeight(view: View, durationMilliseconds: Long, from: Int, to: Int)
    fun animateViewOpacity(view: View, durationMilliseconds: Long)
    fun cancelAnimation()
}