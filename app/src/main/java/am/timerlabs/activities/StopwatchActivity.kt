package am.timerlabs.activities

import am.timerlabs.CountUpTimer
import am.timerlabs.R
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * TODO: Animate views opacity on timer and stopwatch start
 */

class StopwatchActivity : AppCompatActivity() {
    private lateinit var appbarLayout: LinearLayout
    private lateinit var appbarTimer: MaterialButton
    private lateinit var startStopwatchBtn: MaterialButton
    private lateinit var activeStopwatchBackground: View
    private lateinit var stopwatchCurrentSeconds: TextView
    private lateinit var stopwatchActionButtonsLayout: LinearLayout
    private lateinit var pauseResumeStopwatchBtn: MaterialButton
    private lateinit var stopStopwatchBtn: MaterialButton
    private lateinit var countUpTimer: CountUpTimer
    private var tempSec: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        init()
        changeFragment()
        onStartClicked()
        onStopClicked()
        onPauseResumeClicked()
    }

    private fun init() {
        appbarLayout = findViewById(R.id.appbar_layout)
        appbarTimer = findViewById(R.id.appbar_timer)
        startStopwatchBtn = findViewById(R.id.start_stopwatch_button)
        activeStopwatchBackground = findViewById(R.id.active_stopwatch_background)
        stopwatchCurrentSeconds = findViewById(R.id.active_stopwatch_seconds)
        stopwatchActionButtonsLayout = findViewById(R.id.stopwatch_action_buttons)
        pauseResumeStopwatchBtn = findViewById(R.id.pause_resume_stopwatch_button)
        stopStopwatchBtn = findViewById(R.id.stop_stopwatch_button)
    }

    private fun onStartClicked() {
        startStopwatchBtn.setOnClickListener {
            startStopwatchBtn.animate()
                .scaleX(30f)
                .scaleY(30f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(2000)
                .withEndAction {
                    stopwatchCurrentSeconds.visibility = View.VISIBLE
                    stopwatchActionButtonsLayout.visibility = View.VISIBLE
                    appbarLayout.visibility = View.INVISIBLE
                    startStopwatch()

                    startStopwatchBtn.setOnClickListener { null }
                }.start()
        }
    }

    private fun onStopClicked() {
        stopStopwatchBtn.setOnClickListener {
            startStopwatchBtn.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(2000)
                .start()

            stopwatchCurrentSeconds.visibility = View.VISIBLE
            appbarLayout.visibility = View.VISIBLE
            stopwatchActionButtonsLayout.visibility = View.INVISIBLE

            countUpTimer.cancel()
            onStartClicked()
        }
    }

    private fun onPauseResumeClicked() {
        pauseResumeStopwatchBtn.setOnClickListener {
            stopwatchCurrentSeconds.visibility = View.VISIBLE
            stopwatchActionButtonsLayout.visibility = View.VISIBLE
            appbarLayout.visibility = View.INVISIBLE

            if (pauseResumeStopwatchBtn.text == getText(R.string.pause)) {
                pauseResumeStopwatchBtn.text = getText(R.string.resume)
                onStopwatchPause()
            } else {
                pauseResumeStopwatchBtn.text = getText(R.string.pause)
                onStopwatchResume()
            }
        }
    }

    private fun onStopwatchPause() {
        countUpTimer.stop()
        tempSec = stopwatchCurrentSeconds.text.toString().toInt()
    }

    private fun onStopwatchResume() {
        countUpTimer.start()
    }

    private fun startStopwatch() {
        countUpTimer = object : CountUpTimer(1000) {
            override fun onTick(millisElapsed: Long) {
                stopwatchCurrentSeconds.text = ((millisElapsed / 1000) + tempSec).toString()
            }
        }
        countUpTimer.start()
    }


    private fun changeFragment() {
        appbarTimer.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels
}
