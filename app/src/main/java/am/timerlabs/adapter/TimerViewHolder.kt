package am.timerlabs.adapter

import am.timerlabs.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var timerNumber: TextView = view.findViewById(R.id.timer_number)
}