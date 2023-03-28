package dev.future.taxipark.ui.drawer.adapter.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R
import dev.future.taxipark.databinding.HistoryTravelItemBinding
import dev.future.taxipark.ui.drawer.model.OrderHistoryItem

class HistoryTravelAdapter(val context: Context,var callback : CallBackHistory) : RecyclerView.Adapter<HistoryTravelAdapter.HistoryHolder>() {

    interface CallBackHistory{
        fun onClick(item : OrderHistoryItem)
    }

    var items: List<OrderHistoryItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class HistoryHolder(var binding: HistoryTravelItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            HistoryTravelItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    @SuppressLint("SetTextI18n")
    @Suppress("UsePropertyAccessSyntax")
    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        var item = items[position]

        holder.itemView.setOnClickListener {
            callback.onClick(item)
        }

        with(holder.binding) {
            historyTime.text = item.firstCreatedAt + " - " + item.lastCreatedAt + "  " + item.day
            historyCount.setText("${context.resources.getText(R.string.yandex_taksi_buyurtmalar)} (${item.count})")
            historySumm.setText("+${item.totalSumma} ${context.resources.getText(R.string.so_m)}")
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}