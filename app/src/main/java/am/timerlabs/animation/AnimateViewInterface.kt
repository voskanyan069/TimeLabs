package am.timerlabs.animation

import android.view.View

interface AnimateViewInterface {
    fun animateView(view: View, durationMilliseconds: Long, from: Int, to: Int)
    fun cancelAnimation()
}