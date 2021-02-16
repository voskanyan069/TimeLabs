package am.timerlabs.components

import am.timerlabs.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TimerAdapter(private val data: MutableSet<Int>) : RecyclerView.Adapter<TimerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.timers_list_view, parent, false)
        return TimerViewHolder(root)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.timerNumber.text = data.elementAt(position).toString()
    }

    override fun getItemCount(): Int = data.size
}