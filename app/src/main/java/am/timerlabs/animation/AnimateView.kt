package am.timerlabs.animation

import android.animation.ValueAnimator
import android.view.View

class AnimateView {
    companion object : AnimateViewInterface {
        private lateinit var valueAnimator: ValueAnimator

        override fun animateViewHeight(view: View, durationMilliseconds: Long, from: Int, to: Int) {
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

        override fun animateViewOpacity(view: View, durationMilliseconds: Long) {
            valueAnimator = ValueAnimator.ofInt(0, 255)
            valueAnimator.duration = durationMilliseconds
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                view.background.alpha = animatedValue
            }
        }

        override fun cancelAnimation() {
            valueAnimator.cancel()
        }
    }
}