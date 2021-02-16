package am.timerlabs.activities

import am.timerlabs.R
import am.timerlabs.adapter.TimerAdapter
import am.timerlabs.animation.AnimateView
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

/**
 * TODO: Fix bugs | sometimes not playing background animation, or play not correct time
 * TODO: Save EditText custom numbers to array and add to adapter
 * TODO: Next and previous numbers with animation
 */

class TimerActivity : AppCompatActivity() {
    private lateinit var appbarLayout: LinearLayout
    private lateinit var appbarStopwatch: MaterialButton
    private lateinit var customTimerET: EditText
    private lateinit var timersList: DiscreteScrollView
    private lateinit var startTimerBtn: MaterialButton
    private lateinit var stopTimerBtn: MaterialButton
    private lateinit var customTimerBtn: ImageView
    private lateinit var activeTimerBackground: View
    private lateinit var timerCurrentSeconds: TextView
    private lateinit var timer: CountDownTimer
    private val timerNumbers = arrayOf(1, 5, 10, 15, 20, 25, 30)
    private var isTimerEditTextVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        init()
        fillTimersList()
        changeFragment()
        getTimerDuration()
        stopTimer()
        setCustomTime()
    }

    private fun init() {
        appbarLayout = findViewById(R.id.appbar_layout)
        appbarStopwatch = findViewById(R.id.appbar_stopwatch)
        customTimerBtn = findViewById(R.id.custom_time_timer)
        timersList = findViewById(R.id.timer_numbers_list)
        customTimerET = findViewById(R.id.custom_timer_edittext)
        startTimerBtn = findViewById(R.id.start_timer_button)
        stopTimerBtn = findViewById(R.id.stop_timer_button)
        activeTimerBackground = findViewById(R.id.active_timer_background)
        timerCurrentSeconds = findViewById(R.id.active_timer_seconds)
    }

    private fun fillTimersList() {
        timersList.adapter = TimerAdapter(timerNumbers)
        timersList.setItemTransformer(
            ScaleTransformer.Builder()
            .setMinScale(0.5f)
            .build())
    }

    private fun setCustomTime() {
        customTimerBtn.setOnClickListener {
            isTimerEditTextVisible = !isTimerEditTextVisible
            checkTimerVisibility()
        }
    }

    private fun checkTimerVisibility() {
        if (isTimerEditTextVisible) {
            timersList.visibility = View.INVISIBLE
            customTimerET.visibility = View.VISIBLE
        } else {
            timersList.visibility = View.VISIBLE
            customTimerET.visibility = View.INVISIBLE
        }
    }

    private fun getTimerDuration() {
        startTimerBtn.setOnClickListener {
            val seconds: Int = if (isTimerEditTextVisible) {
                customTimerET.text.toString().toInt()
            } else {
                timerNumbers[timersList.currentItem]
            }
            val milliseconds: Long = (seconds * 1000).toLong()
            startTimerBtn.animate()
                .scaleX(30f)
                .scaleY(30f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(1000)
                .withEndAction {
                    timerCurrentSeconds.text = seconds.toString()
                    activeTimerBackground.visibility = View.VISIBLE
                    timerCurrentSeconds.visibility = View.VISIBLE
                    stopTimerBtn.visibility = View.VISIBLE
                    appbarLayout.visibility = View.INVISIBLE
                    customTimerBtn.visibility = View.INVISIBLE
                    timersList.visibility = View.INVISIBLE
                    customTimerET.visibility = View.INVISIBLE
                    startTimerBtn.visibility = View.INVISIBLE
                    startTimerBtn.scaleX = 1f
                    startTimerBtn.scaleY = 1f

                    AnimateView.animateViewHeight(activeTimerBackground, milliseconds, activeTimerBackground.measuredHeight, 0)
                    startTimer(milliseconds)
                }.start()
        }
    }

    private fun startTimer(milliseconds: Long) {
        timer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                println("millisUntilFinished - $millisUntilFinished")
                timerCurrentSeconds.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                println("finished")
                activeTimerBackground.visibility = View.INVISIBLE
                timerCurrentSeconds.visibility = View.INVISIBLE
                stopTimerBtn.visibility = View.INVISIBLE
                appbarLayout.visibility = View.VISIBLE
                customTimerBtn.visibility = View.VISIBLE
                startTimerBtn.visibility = View.VISIBLE
                checkTimerVisibility()
                val layoutParams = activeTimerBackground.layoutParams
                layoutParams.height = getScreenHeight()
                activeTimerBackground.layoutParams = layoutParams
            }
        }
        timer.start()
    }

    private fun stopTimer() {
        stopTimerBtn.setOnClickListener {
            timer.cancel()
            timer.onFinish()
            AnimateView.cancelAnimation()
        }
    }

    private fun changeFragment() {
        appbarStopwatch.setOnClickListener {
            val intent = Intent(this, StopwatchActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels
}
