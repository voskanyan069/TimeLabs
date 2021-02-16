package am.timerlabs.animation

import android.animation.ValueAnimator
import android.view.View

class AnimateView {
    companion object : AnimateViewInterface {
        private lateinit var valueAnimator: ValueAnimator

        override fun animateView(view: View, durationMilliseconds: Long, from: Int, to: Int) {
            valueAnimator = ValueAnimator.ofInt(from, to)
            valueAnimator.duration = durationMilliseconds
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = animatedValue
                view.layoutParams = layoutParams
            }
            valueAnimator.start()
        }

        override fun cancelAnimation() {
            valueAnimator.cancel()
        }
    }
}